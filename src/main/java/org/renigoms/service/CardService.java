package org.renigoms.service;

import lombok.AllArgsConstructor;
import org.renigoms.DTO.CardDTO;
import org.renigoms.interfaces.GenericMethodsI;
import org.renigoms.persistence.DAO.CardDAO;
import org.renigoms.persistence.entity.CardEntity;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

@AllArgsConstructor
public class CardService {

    private final Connection connection;


    public CardEntity insert(CardEntity entity) throws SQLException {
        try {
            CardDAO cardDAO = new CardDAO(connection);
            cardDAO.insert(entity);
            connection.commit();
        } catch (SQLException ex) {
            connection.rollback();
            throw ex;
        }
        return entity;
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
        return null;
    }
}
