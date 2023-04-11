package com.maker.crm.service.clue.impl;

import com.maker.crm.action.abs.constants.Constant;
import com.maker.crm.commons.utils.DateUtil;
import com.maker.crm.commons.utils.UUIDUtils;
import com.maker.crm.dao.clue.ClueActivityRelationMapper;
import com.maker.crm.dao.clue.ClueMapper;
import com.maker.crm.dao.clue.ClueRemarkMapper;
import com.maker.crm.dao.contacts.ContactsActivityRelationMapper;
import com.maker.crm.dao.contacts.ContactsMapper;
import com.maker.crm.dao.contacts.ContactsRemarkMapper;
import com.maker.crm.dao.customer.CustomerMapper;
import com.maker.crm.dao.customer.CustomerRemarkMapper;
import com.maker.crm.dao.tran.TransactionMapper;
import com.maker.crm.dao.tran.TransactionRemarkMapper;
import com.maker.crm.model.*;
import com.maker.crm.service.clue.ClueRemarkService;
import com.maker.crm.service.clue.ClueService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class ClueServiceImpl implements ClueService {
    private static final Logger LOGGER= LoggerFactory.getLogger(ClueServiceImpl.class);
    @Autowired
    private ClueMapper clueMapper;
    @Autowired
    private ClueRemarkMapper clueRemarkMapper;
    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private CustomerRemarkMapper customerRemarkMapper;
    @Autowired
    private ContactsMapper contactsMapper;
    @Autowired
    private ContactsRemarkMapper contactsRemarkMapper;
    @Autowired
    private ClueActivityRelationMapper clueActivityRelationMapper;
    @Autowired
    private ContactsActivityRelationMapper contactsActivityRelationMapper;
    @Autowired
    private TransactionMapper transactionMapper;
    @Autowired
    private TransactionRemarkMapper transactionRemarkMapper;
    @Override
    public int addClue(Clue clue) throws Exception {
        if(clue==null){
            LOGGER.error("传入的线索为空");
            return 0;
        }
        if(!StringUtils.hasLength(clue.getId())){
            LOGGER.error("传入的线索id为空");
            return 0;
        }
        if(!StringUtils.hasLength(clue.getFullname())){
            LOGGER.error("传入线索中，名称为空");
            return 0;
        }
        if(!StringUtils.hasLength(clue.getCompany())){
            LOGGER.error("传入线索中，公司为空");
            return 0;
        }

        return clueMapper.insertClue(clue);
    }

    @Override
    public List<Clue> queryClueSplit(Map<String, Object> map) throws Exception {
        if(map==null){
            LOGGER.error("分页查询线索传入参数为空");
            return null;
        }
        if(map.get("beginNo")==null||"".equals(map.get("beginNo").toString())){
            LOGGER.error("分页查询线索，传入页码参数为空");
        }
        int beginNo=Integer.parseInt(map.get("beginNo").toString());
        if(beginNo<0){
            map.put("beginNo",0);
        }
        if(map.get("pageSize")==null||"".equals(map.get("pageSize").toString())){
            LOGGER.error("分页查询线索，传入每页条数参数为空");
        }
        int pageSize=Integer.parseInt(map.get("pageSize").toString());
        if(pageSize<1){
            map.put("pageSize",2);
        }
        return clueMapper.selectClueSplit(map);
    }

    @Override
    public long queryClueCountByCondition(Map<String, Object> map) throws Exception {
        return clueMapper.selectClueCountByCondition(map);
    }

    @Override
    public Clue queryClueById(String id) throws Exception {
        if(id==null || "".equals(id)){
            LOGGER.error("根据id查询线索传入id为空");
            return null;
        }
        return clueMapper.selectClueById(id);

    }

    @Override
    public void saveClueConvert(Map<String, Object> map) throws Exception {
       //将线索相关信息保存到Customer表中
        Customer customer=new Customer();
        String clueId=(String) map.get("clueId");
        User user=(User) map.get(Constant.SESSION_USER);
        Clue clue=clueMapper.selectClueById(clueId);
        customer.setId(UUIDUtils.getUUID());
        customer.setCreateBy(user.getId());
        customer.setCreateTime(DateUtil.dateToDateTimeStr(new Date()));
        customer.setName(clue.getCompany());
        customer.setAddress(clue.getAddress());
        customer.setWebsite(clue.getWebsite());
        customer.setContactSummary(clue.getContactSummary());
        customer.setNextContactTime(clue.getNextContactTime());
        customer.setOwner(user.getId());
        customer.setDescription(clue.getDescription());
        customer.setPhone(clue.getPhone());
        customerMapper.insertCustomer(customer);
        //将线索相关信息保存到联系人表当中
        Contacts contacts=new Contacts();
        contacts.setId(UUIDUtils.getUUID());
        contacts.setFullname(clue.getFullname());
        contacts.setContactSummary(clue.getContactSummary());
        contacts.setAddress(clue.getAddress());
        contacts.setCreateBy(user.getId());
        contacts.setCreateTime(DateUtil.dateToDateTimeStr(new Date()));
        contacts.setDescription(clue.getDescription());
        contacts.setAppellation(clue.getAppellation());
        contacts.setCustomerId(customer.getId());
        contacts.setEmail(clue.getEmail());
        contacts.setJob(clue.getJob());
        contacts.setMphone(clue.getMphone());
        contacts.setNextContactTime(clue.getNextContactTime());
        contacts.setOwner(user.getId());
        contacts.setSource(clue.getSource());
        contactsMapper.insertContacts(contacts);
        //查询所有的线索备注，并存放到客户备注表和联系人备注表中
      List<ClueRemark> clueRemarkList= clueRemarkMapper.selectRemarkByClueId(clueId);
      if(clueRemarkList!=null && clueRemarkList.size()>0){
          List<ContactsRemark> contactsRemarkList=new ArrayList<>();
          ContactsRemark contactsRemark=null;
          List<CustomerRemark> customerRemarkList=new ArrayList<>();
          CustomerRemark customerRemark=null;
            for(ClueRemark remark:clueRemarkList){
                //添加联系人备注
                contactsRemark=new ContactsRemark();
                contactsRemark.setId(UUIDUtils.getUUID());
                contactsRemark.setContactsId(contacts.getId());
                contactsRemark.setCreateBy(remark.getCreateBy());
                contactsRemark.setCreateTime(remark.getCreateTime());
                contactsRemark.setEditBy(remark.getEditBy());
                contactsRemark.setEditTime(remark.getEditTime());
                contactsRemark.setNoteContent(remark.getNoteContent());
                contactsRemarkList.add(contactsRemark);

                //添加顾客备注
                customerRemark=new CustomerRemark();
                customerRemark.setId(UUIDUtils.getUUID());
                customerRemark.setCustomerId(customer.getId());
                customerRemark.setCreateBy(remark.getCreateBy());
                customerRemark.setCreateTime(remark.getCreateTime());
                customerRemark.setEditBy(remark.getEditBy());
                customerRemark.setEditTime(remark.getEditTime());
                customerRemark.setNoteContent(remark.getNoteContent());
                customerRemarkList.add(customerRemark);
            }
            customerRemarkMapper.insertCustomerRemarkBatch(customerRemarkList);
            contactsRemarkMapper.insertContactsRemarkBatch(contactsRemarkList);
      }
        //根据ClueId查询市场活动,并将其转到联系人与市场活动关系表中
            List<ClueActivityRelation> clueActivityRelations=clueActivityRelationMapper.selectClueActivityRelationByCid(clueId);
            if(clueActivityRelations!=null && clueActivityRelations.size()>0){
                List<ContactsActivityRelation> contactsActivityRelations=new ArrayList<>();
                ContactsActivityRelation contactsActivityRelation=null;
                for (ClueActivityRelation relation : clueActivityRelations){
                    contactsActivityRelation=new ContactsActivityRelation();
                    contactsActivityRelation.setId(UUIDUtils.getUUID());
                    contactsActivityRelation.setActivityId(relation.getActivityId());
                    contactsActivityRelation.setContactsId(contacts.getId());
                    contactsActivityRelations.add(contactsActivityRelation);
                }
                contactsActivityRelationMapper.insertContactsActivityRelationBatch(contactsActivityRelations);
            }
            String isTran=(String) map.get("isTran");
            if("true".equals(isTran)){
                //需要创建交易
                Transaction transaction=new Transaction();
                transaction.setId(UUIDUtils.getUUID());
                transaction.setActivityId((String) map.get("activityId"));
                transaction.setContactsId(contacts.getId());
                transaction.setContactSummary(clue.getContactSummary());
                transaction.setCreateBy(user.getId());
                transaction.setCreateTime(DateUtil.dateToDateTimeStr(new Date()));
                transaction.setCustomerId(customer.getId());
                transaction.setOwner(user.getId());
                transaction.setExpectedDate((String) map.get("expectedDate"));
                transaction.setMoney((String) map.get("money"));
                transaction.setName((String) map.get("name"));
                transaction.setDescription(clue.getDescription());
                transaction.setSource(clue.getSource());
                transaction.setStage((String) map.get("stage"));
                transactionMapper.insertTransaction(transaction);
                //将线索的备注转到交易备注中
                if(clueRemarkList!=null && clueRemarkList.size()>0){
                    List<TransactionRemark> transactionRemarks=new ArrayList<>();
                    TransactionRemark transactionRemark=null;
                    for(ClueRemark remark : clueRemarkList){
                        transactionRemark=new TransactionRemark();
                        transactionRemark.setCreateBy(remark.getCreateBy());
                        transactionRemark.setCreateTime(remark.getCreateTime());
                        transactionRemark.setEditBy(remark.getEditBy());
                        transactionRemark.setEditTime(remark.getEditTime());
                        transactionRemark.setEditFlag(remark.getEditFlag());
                        transactionRemark.setNoteContent(remark.getNoteContent());
                        transactionRemark.setId(UUIDUtils.getUUID());
                        transactionRemark.setTranId(transaction.getId());
                        transactionRemarks.add(transactionRemark);
                    }
                    transactionRemarkMapper.insertTransactionRemarkBatch(transactionRemarks);
                }
            }
            //删除线索市场活动关联
            clueActivityRelationMapper.deleteClueActivityRelationByCid(clueId);
            //删除线索备注
            clueRemarkMapper.deleteClueRemarkByCid(clueId);
            //删除线索
            clueMapper.deleteByPrimaryKey(clueId);

    }
}
