package com.maker.crm.dao.dictionary;

import com.maker.crm.model.DictionaryType;

import java.util.List;

public interface DictionaryTypeMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_dic_type
     *
     * @mbggenerated Tue Feb 28 12:04:14 CST 2023
     */
    int deleteByPrimaryKey(String code);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_dic_type
     *
     * @mbggenerated Tue Feb 28 12:04:14 CST 2023
     */
    int insert(DictionaryType record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_dic_type
     *
     * @mbggenerated Tue Feb 28 12:04:14 CST 2023
     */
    int insertSelective(DictionaryType record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_dic_type
     *
     * @mbggenerated Tue Feb 28 12:04:14 CST 2023
     */
    DictionaryType selectByPrimaryKey(String code);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_dic_type
     *
     * @mbggenerated Tue Feb 28 12:04:14 CST 2023
     */
    int updateByPrimaryKeySelective(DictionaryType record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_dic_type
     *
     * @mbggenerated Tue Feb 28 12:04:14 CST 2023
     */
    int updateByPrimaryKey(DictionaryType record);

    DictionaryType selectDicTypeByCode(String code);
    int insertDicType(DictionaryType dictionaryType) throws Exception;

    List<DictionaryType> selectAllDicType()throws Exception;
}