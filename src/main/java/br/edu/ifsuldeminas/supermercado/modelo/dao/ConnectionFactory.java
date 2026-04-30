/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.ifsuldeminas.supermercado.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ConnectionFactory {
    
    private static final String DB_URL = "jdbc:mysql://localhost:3306/supermercado?useSSL=false";
    private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "1234";

    private static ConnectionFactory instance;

    
    private ConnectionFactory() {
        try {
            Class.forName(DB_DRIVER);
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException("Driver do banco de dados não encontrado. " + ex);
        }
    }
    //método publico estátio que permite o acesso a instância única da ConnectioFactory
    public static synchronized ConnectionFactory getInstance() {
        if (instance == null) {
            instance = new ConnectionFactory();
        }
        return instance;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }
    
    public PreparedStatement getPreparedStatement (String sql) throws SQLException{
        Connection con = getConnection();
        return con.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS);
    }
    
    //PreparedStatement.RETURN_GENERATED_KEYS - > recupera o ultimo id gerado no autoincrement da tabela após inserção

   
}