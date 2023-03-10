package com.maker.crm.model;

public class User {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tbl_user.id
     *
     * @mbggenerated Thu Jan 12 11:17:11 CST 2023
     */
    private String id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tbl_user.login_act
     *
     * @mbggenerated Thu Jan 12 11:17:11 CST 2023
     */
    private String loginAct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tbl_user.name
     *
     * @mbggenerated Thu Jan 12 11:17:11 CST 2023
     */
    private String name;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tbl_user.login_pwd
     *
     * @mbggenerated Thu Jan 12 11:17:11 CST 2023
     */
    private String loginPwd;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tbl_user.email
     *
     * @mbggenerated Thu Jan 12 11:17:11 CST 2023
     */
    private String email;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tbl_user.expire_time
     *
     * @mbggenerated Thu Jan 12 11:17:11 CST 2023
     */
    private String expireTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tbl_user.lock_stat
     *
     * @mbggenerated Thu Jan 12 11:17:11 CST 2023
     */
    private String lockStat;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tbl_user.dept_no
     *
     * @mbggenerated Thu Jan 12 11:17:11 CST 2023
     */
    private String deptNo;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tbl_user.allow_ip
     *
     * @mbggenerated Thu Jan 12 11:17:11 CST 2023
     */
    private String allowIp;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tbl_user.create_time
     *
     * @mbggenerated Thu Jan 12 11:17:11 CST 2023
     */
    private String createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tbl_user.create_by
     *
     * @mbggenerated Thu Jan 12 11:17:11 CST 2023
     */
    private String createBy;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tbl_user.edit_time
     *
     * @mbggenerated Thu Jan 12 11:17:11 CST 2023
     */
    private String editTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tbl_user.edit_by
     *
     * @mbggenerated Thu Jan 12 11:17:11 CST 2023
     */
    private String editBy;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tbl_user.id
     *
     * @return the value of tbl_user.id
     *
     * @mbggenerated Thu Jan 12 11:17:11 CST 2023
     */
    public String getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tbl_user.id
     *
     * @param id the value for tbl_user.id
     *
     * @mbggenerated Thu Jan 12 11:17:11 CST 2023
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tbl_user.login_act
     *
     * @return the value of tbl_user.login_act
     *
     * @mbggenerated Thu Jan 12 11:17:11 CST 2023
     */
    public String getLoginAct() {
        return loginAct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tbl_user.login_act
     *
     * @param loginAct the value for tbl_user.login_act
     *
     * @mbggenerated Thu Jan 12 11:17:11 CST 2023
     */
    public void setLoginAct(String loginAct) {
        this.loginAct = loginAct == null ? null : loginAct.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tbl_user.name
     *
     * @return the value of tbl_user.name
     *
     * @mbggenerated Thu Jan 12 11:17:11 CST 2023
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tbl_user.name
     *
     * @param name the value for tbl_user.name
     *
     * @mbggenerated Thu Jan 12 11:17:11 CST 2023
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tbl_user.login_pwd
     *
     * @return the value of tbl_user.login_pwd
     *
     * @mbggenerated Thu Jan 12 11:17:11 CST 2023
     */
    public String getLoginPwd() {
        return loginPwd;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tbl_user.login_pwd
     *
     * @param loginPwd the value for tbl_user.login_pwd
     *
     * @mbggenerated Thu Jan 12 11:17:11 CST 2023
     */
    public void setLoginPwd(String loginPwd) {
        this.loginPwd = loginPwd == null ? null : loginPwd.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tbl_user.email
     *
     * @return the value of tbl_user.email
     *
     * @mbggenerated Thu Jan 12 11:17:11 CST 2023
     */
    public String getEmail() {
        return email;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tbl_user.email
     *
     * @param email the value for tbl_user.email
     *
     * @mbggenerated Thu Jan 12 11:17:11 CST 2023
     */
    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tbl_user.expire_time
     *
     * @return the value of tbl_user.expire_time
     *
     * @mbggenerated Thu Jan 12 11:17:11 CST 2023
     */
    public String getExpireTime() {
        return expireTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tbl_user.expire_time
     *
     * @param expireTime the value for tbl_user.expire_time
     *
     * @mbggenerated Thu Jan 12 11:17:11 CST 2023
     */
    public void setExpireTime(String expireTime) {
        this.expireTime = expireTime == null ? null : expireTime.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tbl_user.lock_stat
     *
     * @return the value of tbl_user.lock_stat
     *
     * @mbggenerated Thu Jan 12 11:17:11 CST 2023
     */
    public String getLockStat() {
        return lockStat;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tbl_user.lock_stat
     *
     * @param lockStat the value for tbl_user.lock_stat
     *
     * @mbggenerated Thu Jan 12 11:17:11 CST 2023
     */
    public void setLockStat(String lockStat) {
        this.lockStat = lockStat == null ? null : lockStat.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tbl_user.dept_no
     *
     * @return the value of tbl_user.dept_no
     *
     * @mbggenerated Thu Jan 12 11:17:11 CST 2023
     */
    public String getDeptNo() {
        return deptNo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tbl_user.dept_no
     *
     * @param deptNo the value for tbl_user.dept_no
     *
     * @mbggenerated Thu Jan 12 11:17:11 CST 2023
     */
    public void setDeptNo(String deptNo) {
        this.deptNo = deptNo == null ? null : deptNo.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tbl_user.allow_ip
     *
     * @return the value of tbl_user.allow_ip
     *
     * @mbggenerated Thu Jan 12 11:17:11 CST 2023
     */
    public String getAllowIp() {
        return allowIp;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tbl_user.allow_ip
     *
     * @param allowIp the value for tbl_user.allow_ip
     *
     * @mbggenerated Thu Jan 12 11:17:11 CST 2023
     */
    public void setAllowIp(String allowIp) {
        this.allowIp = allowIp == null ? null : allowIp.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tbl_user.create_time
     *
     * @return the value of tbl_user.create_time
     *
     * @mbggenerated Thu Jan 12 11:17:11 CST 2023
     */
    public String getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tbl_user.create_time
     *
     * @param createTime the value for tbl_user.create_time
     *
     * @mbggenerated Thu Jan 12 11:17:11 CST 2023
     */
    public void setCreateTime(String createTime) {
        this.createTime = createTime == null ? null : createTime.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tbl_user.create_by
     *
     * @return the value of tbl_user.create_by
     *
     * @mbggenerated Thu Jan 12 11:17:11 CST 2023
     */
    public String getCreateBy() {
        return createBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tbl_user.create_by
     *
     * @param createBy the value for tbl_user.create_by
     *
     * @mbggenerated Thu Jan 12 11:17:11 CST 2023
     */
    public void setCreateBy(String createBy) {
        this.createBy = createBy == null ? null : createBy.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tbl_user.edit_time
     *
     * @return the value of tbl_user.edit_time
     *
     * @mbggenerated Thu Jan 12 11:17:11 CST 2023
     */
    public String getEditTime() {
        return editTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tbl_user.edit_time
     *
     * @param editTime the value for tbl_user.edit_time
     *
     * @mbggenerated Thu Jan 12 11:17:11 CST 2023
     */
    public void setEditTime(String editTime) {
        this.editTime = editTime == null ? null : editTime.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tbl_user.edit_by
     *
     * @return the value of tbl_user.edit_by
     *
     * @mbggenerated Thu Jan 12 11:17:11 CST 2023
     */
    public String getEditBy() {
        return editBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tbl_user.edit_by
     *
     * @param editBy the value for tbl_user.edit_by
     *
     * @mbggenerated Thu Jan 12 11:17:11 CST 2023
     */
    public void setEditBy(String editBy) {
        this.editBy = editBy == null ? null : editBy.trim();
    }
}