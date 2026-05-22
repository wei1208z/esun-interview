package com.esun.bank.repository;

import com.esun.bank.model.FavoriteDTO;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.StatementType;
import java.util.List;
import java.util.Map;

@Mapper
public interface FavoriteRepository {

    @Select("{ CALL sp_GetFavoriteList() }")
    @Options(statementType = StatementType.CALLABLE)
    List<FavoriteDTO> callGetFavoriteList();

    @Select("{ CALL sp_GetFavoriteById(#{sn, mode=IN, jdbcType=INTEGER}) }")
    @Options(statementType = StatementType.CALLABLE)
    Map<String, Object> callGetFavoriteById(@Param("sn") Integer sn);

    @Insert("{ CALL sp_InsertFavorite(#{userId, mode=IN, jdbcType=VARCHAR}, #{productNo, mode=IN, jdbcType=INTEGER}, #{quantity, mode=IN, jdbcType=INTEGER}, #{account, mode=IN, jdbcType=VARCHAR}, #{totalFee, mode=IN, jdbcType=DECIMAL}, #{totalAmount, mode=IN, jdbcType=DECIMAL}) }")
    @Options(statementType = StatementType.CALLABLE)
    void callInsertFavorite(Map<String, Object> params);

    @Update("{ CALL sp_UpdateFavorite(#{sn, mode=IN, jdbcType=INTEGER}, #{quantity, mode=IN, jdbcType=INTEGER}, #{account, mode=IN, jdbcType=VARCHAR}, #{totalFee, mode=IN, jdbcType=DECIMAL}, #{totalAmount, mode=IN, jdbcType=DECIMAL}) }")
    @Options(statementType = StatementType.CALLABLE)
    void callUpdateFavorite(Map<String, Object> params);

    @Delete("{ CALL sp_DeleteFavorite(#{sn, mode=IN, jdbcType=INTEGER}) }")
    @Options(statementType = StatementType.CALLABLE)
    void callDeleteFavorite(@Param("sn") Integer sn);

    @Select("{ CALL sp_GetAllProducts() }")
    @Options(statementType = StatementType.CALLABLE)
    List<Map<String, Object>> callGetAllProducts();

    @Select("SELECT Price, FeeRate FROM Product WHERE No = #{productNo}")
    Map<String, Object> findProductById(@Param("productNo") Integer productNo);
}