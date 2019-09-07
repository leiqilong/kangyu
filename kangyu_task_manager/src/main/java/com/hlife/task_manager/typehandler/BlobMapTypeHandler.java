package com.hlife.task_manager.typehandler;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.sql.*;
import java.util.Map;

public class BlobMapTypeHandler extends BaseTypeHandler<Map<String, Object>> {

    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, Map<String, Object> stringObjectMap, JdbcType jdbcType) throws SQLException {

    }

    @Override
    public Map<String, Object> getNullableResult(ResultSet resultSet, String columnName) throws SQLException {
        Blob blob = resultSet.getBlob(columnName);
        byte[] returnValue = null;
        if (null != blob) {
            returnValue = blob.getBytes(1, (int) blob.length());
        }
        try {
            ByteArrayInputStream byteInt = new ByteArrayInputStream(returnValue);
            ObjectInputStream objInt = new ObjectInputStream(byteInt);

            return (Map<String, Object>) objInt.readObject();//byte[]转map
        } catch (Exception e) {
            throw new RuntimeException("Blob Encoding Error!");
        }
    }

    @Override
    public Map<String, Object> getNullableResult(ResultSet resultSet, int columnIndex) throws SQLException {
        Blob blob = resultSet.getBlob(columnIndex);
        byte[] returnValue = null;
        if (null != blob) {
            returnValue = blob.getBytes(1, (int) blob.length());
        }
        try {
            ByteArrayInputStream byteInt = new ByteArrayInputStream(returnValue);
            ObjectInputStream objInt = new ObjectInputStream(byteInt);

            return (Map<String, Object>) objInt.readObject();//byte[]转map
        } catch (Exception e) {
            throw new RuntimeException("Blob Encoding Error!");
        }
    }

    @Override
    public Map<String, Object> getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        return null;
    }
}
