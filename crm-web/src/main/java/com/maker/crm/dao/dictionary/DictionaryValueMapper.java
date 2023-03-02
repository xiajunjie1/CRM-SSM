package com.maker.crm.dao.dictionary;

import com.maker.crm.model.DictionaryValue;

import java.util.List;

public interface DictionaryValueMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_dic_value
     *
     * @mbggenerated Tue Feb 28 12:04:14 CST 2023
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_dic_value
     *
     * @mbggenerated Tue Feb 28 12:04:14 CST 2023
     */
    int insert(DictionaryValue record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_dic_value
     *
     * @mbggenerated Tue Feb 28 12:04:14 CST 2023
     */
    int insertSelective(DictionaryValue record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_dic_value
     *
     * @mbggenerated Tue Feb 28 12:04:14 CST 2023
     */
    DictionaryValue selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_dic_value
     *
     * @mbggenerated Tue Feb 28 12:04:14 CST 2023
     */
    int updateByPrimaryKeySelective(DictionaryValue record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_dic_value
     *
     * @mbggenerated Tue Feb 28 12:04:14 CST 2023
     */
    int updateByPrimaryKey(DictionaryValue record);

    int insertDictionaryValue(DictionaryValue dictionaryValue);
    List<DictionaryValue> selectAllDictionaryValue()throws Exception;
}