package org.vaadin.example.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.vaadin.example.data.ShovelingPlace;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class SnowRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public SnowRepository(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    // 雪かき場所をDBに追加するメソッド
    public void insertShovelingPlace(ShovelingPlace shovelingPlace){

        String sql =
                """
                INSERT INTO shovelingplace
                (ward, town, jyo, tyo, ban, gou, others, availability)
                VALUES(?, ?, ?, ?, ?, ?, ?, true);
                """;

        jdbcTemplate.update(sql,
                shovelingPlace.getWard(),
                shovelingPlace.getTown(),
                shovelingPlace.getJyo(),
                shovelingPlace.getTyo(),
                shovelingPlace.getBan(),
                shovelingPlace.getGou(),
                shovelingPlace.getOthers());

    }

    // 雪かき場所一覧を取得するメソッド
    public List<ShovelingPlace> getShovelingPlaceList(){

        String sql =
                """
                SELECT *
                FROM shovelingplace
                WHERE availability = true;    
                """;

        return jdbcTemplate.query(sql, new DataClassRowMapper<>(ShovelingPlace.class));

    }

    // 雪かきを引き受けることでavailabilityをfalseにするメソッド（id列の値が必要）
    public void updateAvailability(Long id){

        String sql =
                """
                UPDATE shovelingplace
                SET availability = false
                WHERE id = ?;
                """;

        jdbcTemplate.update(sql, id);

    }

}
