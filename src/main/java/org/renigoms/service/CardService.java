package org.renigoms.service;

import lombok.AllArgsConstructor;
import org.renigoms.DTO.BoardColumnInfoDTO;
import org.renigoms.DTO.CardDTO;
import org.renigoms.exceptions.CardBlockException;
import org.renigoms.exceptions.CardFinishedException;
import org.renigoms.exceptions.EntityNotFoundException;
import org.renigoms.exceptions.ErrorMessage;
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
            return entity;
        } catch (SQLException ex) {
            connection.rollback();
            throw ex;
        }
    }

    public Void delete(Long id) throws SQLException {
        try{
            CardDAO cardDAO = new CardDAO(connection);
            cardDAO.delete(id);
            connection.commit();
        }catch (SQLException ex){
            connection.rollback();
            throw ex;
        }
        return null;
    }

    public Optional<CardDTO> findById(Long id) throws SQLException {
       CardDAO cardDAO = new CardDAO(connection);
       return cardDAO.findById(id);
    }

    public void moveCardToNextColumn(Long cardId, List<BoardColumnInfoDTO> boardColumnInfoDTOS) throws SQLException {
        try{
            CardDAO cardDAO = new CardDAO(connection);
            Optional<CardDTO> optional = cardDAO.findById(cardId);

            CardDTO cardDTO = optional.orElseThrow(() -> new EntityNotFoundException(ENTITY_CARD_ERROR.getMessage()
                    .formatted(cardId)));

            if (cardDTO.blocked())
                throw new CardBlockException(CARD_BLOCK_EXCEPTION
                        .getMessage().formatted(cardId));

            BoardColumnInfoDTO currentColumn = boardColumnInfoDTOS.stream()
                    .filter(bc -> bc.id().equals(cardDTO.columnId()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalStateException(ILLEGAL_STATE_EXCEPTION.getMessage()));

            if (currentColumn.kind().equals(BoardColumnKindEnum.FINAL)) {
                throw new CardFinishedException(ErrorMessage.CARD_FINISHED_EXCEPTION.getMessage());
            }

            BoardColumnInfoDTO nextColumn = boardColumnInfoDTOS.stream()
                    .filter(bc -> bc.order() == currentColumn.order() + 1)
                    .findFirst().orElseThrow();

            cardDAO.moveToColumns(nextColumn.id(), cardId);
            connection.commit();
        }catch(SQLException ex){
            connection.rollback();
            throw ex;
        }
    }
}
