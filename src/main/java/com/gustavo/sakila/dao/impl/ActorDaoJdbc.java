package com.gustavo.sakila.dao.impl;

import com.gustavo.sakila.config.DB;
import com.gustavo.sakila.dao.ActorDao;
import com.gustavo.sakila.entity.Actor;

import javax.sql.DataSource;
import java.sql.*;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ActorDaoJdbc implements ActorDao {
    private final DataSource ds = DB.get();

    private Actor map(ResultSet rs) throws SQLException {
        Actor a = new Actor();
        a.setActorId(rs.getInt("actor_id"));
        a.setFirstName(rs.getString("first_name"));
        a.setLastName(rs.getString("last_name"));
        Timestamp ts = rs.getTimestamp("last_update");
        if (ts != null) a.setLastUpdate(ts.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        return a;
    }

    @Override public Actor post(Actor e){
        String sql = "INSERT INTO actor(first_name,last_name,last_update) VALUES(?,?,NOW())";
        try (Connection c=ds.getConnection();
             PreparedStatement ps=c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            ps.setString(1, e.getFirstName());
            ps.setString(2, e.getLastName());
            ps.executeUpdate();
            try(ResultSet k=ps.getGeneratedKeys()){ if(k.next()) e.setActorId(k.getInt(1)); }
            return e;
        } catch (SQLException ex){ throw new RuntimeException(ex); }
    }

    @Override public Optional<Actor> getById(Integer id){
        String sql="SELECT actor_id,first_name,last_name,last_update FROM actor WHERE actor_id=?";
        try(Connection c=ds.getConnection(); PreparedStatement ps=c.prepareStatement(sql)){
            ps.setInt(1,id);
            try(ResultSet rs=ps.executeQuery()){ return rs.next()? Optional.of(map(rs)) : Optional.empty(); }
        } catch (SQLException ex){ throw new RuntimeException(ex); }
    }

    @Override public List<Actor> getAll(){
        String sql="SELECT actor_id,first_name,last_name,last_update FROM actor ORDER BY actor_id";
        List<Actor> out = new ArrayList<>();
        try(Connection c=ds.getConnection();
            PreparedStatement ps=c.prepareStatement(sql);
            ResultSet rs=ps.executeQuery()){
            while(rs.next()) out.add(map(rs));
        } catch (SQLException ex){ throw new RuntimeException(ex); }
        return out;
    }

    @Override public Actor put(Actor e){
        String sql="UPDATE actor SET first_name=?, last_name=?, last_update=NOW() WHERE actor_id=?";
        try(Connection c=ds.getConnection(); PreparedStatement ps=c.prepareStatement(sql)){
            ps.setString(1, e.getFirstName());
            ps.setString(2, e.getLastName());
            ps.setInt(3, e.getActorId());
            ps.executeUpdate();
            return e;
        } catch (SQLException ex){ throw new RuntimeException(ex); }
    }

    @Override public boolean delete(Integer id){
        String sql="DELETE FROM actor WHERE actor_id=?";
        try(Connection c=ds.getConnection(); PreparedStatement ps=c.prepareStatement(sql)){
            ps.setInt(1, id);
            return ps.executeUpdate()>0;
        } catch (SQLException ex){ throw new RuntimeException(ex); }
    }
}
