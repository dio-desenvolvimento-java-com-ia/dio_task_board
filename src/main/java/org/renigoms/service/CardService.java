package org.renigoms.service;

import lombok.AllArgsConstructor;
import org.renigoms.DTO.BoardColumnInfoDTO;
import org.renigoms.DTO.CardDTO;
import org.renigoms.exceptions.CardBlockException;
import org.renigoms.exceptions.CardFinishedException;
import org.renigoms.exceptions.EntityNotFoundException;
import org.renigoms.exceptions.ErrorMessage;
import org.renigoms.persistence.DAO.BlockDAO;
import org.renigoms.persistence.DAO.CardDAO;
import org.renigoms.persistence.entity.BoardColumnKindEnum;
import org.renigoms.persistence.entity.CardEntity;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.renigoms.exceptions.ErrorMessage.*;

@AllArgsConstructor
public class CardService {

    private final Connection connection;


    public CardEntity insert(CardEntity entity) throws SQLException {
        try {
            CardDAO cardDAO = new CardDAO(connection);
            cardDAO.insert(entity);
            connection.commit();
        } catch (SQLException ex) {
            throwExceptions(connection, ex);
        }
        return entity;
    }

    public void cancel(Long cardId, List<BoardColumnInfoDTO> boardColumnInfoDTOS, Long cancelColumnId) throws SQLException {
        try {
            CardDAO cardDAO = new CardDAO(connection);
            checkIfCanBeMoved(cardId, boardColumnInfoDTOS, cardDAO);
            cardDAO.moveToColumns(cancelColumnId, cardId);
            connection.commit();
        } catch (SQLException ex) {
            throwExceptions(connection, ex);
        }
    }

    public Optional<CardDTO> findById(Long id) throws SQLException {
       CardDAO cardDAO = new CardDAO(connection);
       return cardDAO.findById(id);
    }

    public void moveCardToNextColumn(Long cardId, List<BoardColumnInfoDTO> boardColumnInfoDTOS) throws SQLException {
        try{
            CardDAO cardDAO = new CardDAO(connection);
            BoardColumnInfoDTO nextColumn = checkIfCanBeMoved(cardId, boardColumnInfoDTOS, cardDAO);
            cardDAO.moveToColumns(nextColumn.id(), cardId);
            connection.commit();
        } catch (SQLException ex) {
            throwExceptions(connection, ex);
        }
    }

    public void block(Long id, String reason, List<BoardColumnInfoDTO> boardColumnInfoDTOS) throws SQLException {
        try {
            CardDAO cardDAO = new CardDAO(connection);
            CardDTO cardDTO = checkCardBlock(cardDAO, id, CARD_BLOCK_EXCEPTION
                    .getMessage().substring(0, 24).formatted(id));
            BoardColumnInfoDTO currentColumn = boardColumnInfoDTOS
                    .stream()
                    .filter(bc -> bc.id().equals(cardDTO.columnId()))
                    .findFirst()
                    .orElseThrow();

            if (currentColumn.kind().equals(BoardColumnKindEnum.FINAL) ||
                    currentColumn.kind().equals(BoardColumnKindEnum.CANCEL)){
                throw new IllegalStateException(CARD_TYPE_ERROR.getMessage()
                        .formatted(currentColumn.kind()));
            }

            BlockDAO blockDAO =  new BlockDAO(connection);
            blockDAO.block(id, reason);
            connection.commit();
        } catch (SQLException e) {
            throwExceptions(connection, e);
        }
    }

    public void unblock(Long id, String reason) throws SQLException {
        try{
            CardDAO cardDAO = new CardDAO(connection);
            Optional<CardDTO> optional = cardDAO.findById(id);
            CardDTO cardDTO = optional.orElseThrow(() -> new EntityNotFoundException(ENTITY_CARD_ERROR.getMessage()
                    .formatted(id)));

            if (!cardDTO.blocked())
                throw new CardBlockException(CARD_UNBLOCKED.getMessage().formatted(id));
            BlockDAO blockDAO = new BlockDAO(connection);
            blockDAO.unblock(id, reason);
            connection.commit();
        }catch (SQLException e){
            throwExceptions(connection, e);
        }
    }

    private BoardColumnInfoDTO checkIfCanBeMoved(Long cardId, List<BoardColumnInfoDTO> boardColumnInfoDTOS, CardDAO cardDAO) throws SQLException {
        CardDTO cardDTO = checkCardBlock(cardDAO, cardId, CARD_BLOCK_EXCEPTION
                .getMessage().formatted(cardId));
        BoardColumnInfoDTO currentColumn = boardColumnInfoDTOS.stream()
                .filter(bc -> bc.id().equals(cardDTO.columnId()))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException(ILLEGAL_STATE_EXCEPTION.getMessage()));

        if (currentColumn.kind().equals(BoardColumnKindEnum.FINAL)) {
            throw new CardFinishedException(ErrorMessage.CARD_FINISHED_EXCEPTION.getMessage());
        }

        return boardColumnInfoDTOS.stream()
                .filter(bc -> bc.order() == currentColumn.order() + 1)
                .findFirst().orElseThrow(() -> new IllegalStateException(ErrorMessage.CANCELED_CARD.getMessage()));

    }

    private CardDTO checkCardBlock(CardDAO cardDAO, Long cardId, String messageError) throws SQLException {
        Optional<CardDTO> optional = cardDAO.findById(cardId);

        CardDTO cardDTO = optional.orElseThrow(() -> new EntityNotFoundException(ENTITY_CARD_ERROR.getMessage()
                .formatted(cardId)));

        if (cardDTO.blocked())
            throw new CardBlockException(messageError);

        return cardDTO;
    }


}
