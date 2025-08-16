package org.renigoms.interfaces;

import java.sql.SQLException;
import java.util.Optional;

public interface GenericMethodsI<T, I>{
     T insert(T entity) throws SQLException;
     I delete(final  Long id) throws  SQLException;
     Optional<T> findById(final Long id)throws  SQLException;
}
