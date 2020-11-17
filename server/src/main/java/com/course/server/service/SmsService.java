package com.course.server.service;

import com.course.server.domain.Sms;
import com.course.server.domain.SmsExample;
import com.course.server.dto.ResponseDto;
import com.course.server.dto.SmsDto;
import com.course.server.dto.PageDto;
import com.course.server.enums.SmsStatusEnum;
import com.course.server.enums.SmsUseEnum;
import com.course.server.exception.BusinessException;
import com.course.server.exception.BusinessExceptionCode;
import com.course.server.mapper.SmsMapper;
import com.course.server.util.CopyUtil;
import com.course.server.util.UuidUtil;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import com.github.pagehelper.PageHelper;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

@Service
public class SmsService {

    private  static final Logger LOG = LoggerFactory.getLogger(SmsService.class);

    @Resource
    private SmsMapper  smsMapper;

    /**
     * 页面显示数据list方法
     * @param pageDto
     * @return
     */
    public void list(PageDto pageDto){
        //pageNum:起始条数，pageSize：每页大小
        //插件分页语句的规则，嗲用startPage方法后执行的第一个select语句会进行分页
        PageHelper.startPage(pageDto.getPage(),pageDto.getSize());
        SmsExample smsExample = new SmsExample();
        //根据sort排序
        List<Sms> smsList = smsMapper.selectByExample(smsExample);
        //将结果传递到pageInfo中
        PageInfo<Sms> pageInfo = new PageInfo<Sms>(smsList);
        //设定总数
        pageDto.setTotal(pageInfo.getTotal());
        List<SmsDto> smsDtoList = CopyUtil.copyList(smsList, SmsDto.class);
        //设定list
        pageDto.setList(smsDtoList);
    }

    /**
     * 保存/修改方法
     * @param smsDto
     */
    public void save (SmsDto smsDto){
        //new 目标类,对其进行操作
        //Sms smsSave = new Sms();
        //copy DTO数据传输到实体类中
        //smsDto.setId(UuidUtil.getShortUuid());
        //BeanUtils.copyProperties(smsDto,smsSave);

        Sms sms = CopyUtil.copy(smsDto,Sms.class);
        if(StringUtils.isEmpty(smsDto.getId())){
            this.insert(sms);
        }else{
            this.update(sms);
        }
    }

    /**
     * 插入
     * @param sms
     */
    private void insert (Sms sms){
        Date now = new Date();
        sms.setId(UuidUtil.get32UUID());
        smsMapper.insert(sms);
    }


    /**
     * 修改
     * @param sms
     */
    private void update (Sms sms){
        Date now = new Date();
        smsMapper.updateByPrimaryKey(sms);
    }

    /**
     * 删除
     * @param id
     */
    public void delete (String id){
        smsMapper.deleteByPrimaryKey(id);
    }

    /**
     * 手机发送验证码
     * 同一个手机号操作1分钟内不能重复发送短信
     * smsDto
     * @param smsDto
     */
    public void sendCode(SmsDto smsDto){
        SmsExample example = new SmsExample();
        SmsExample.Criteria criteria = example.createCriteria();
        criteria.andMobileEqualTo(smsDto.getMobile())//根据手机号
                .andUseEqualTo(smsDto.getUse())      //根据用途查找
                .andStatusEqualTo(SmsStatusEnum.NOT_USED.getCode())//未使用
                //跟当前时间比较在一分钟之内
                .andAtGreaterThan(new Date(new Date().getTime()-1 * 60 * 1000));
        List<Sms> smsList = smsMapper.selectByExample(example);

        if(smsList == null || smsList.size()==0){
            saveAndSend(smsDto);
        }else{
            LOG.info("短信请求太过频繁,{}",smsDto.getMobile());
            throw new BusinessException(BusinessExceptionCode.MOBILE_CODE_TOO_FREQUENT);
        }

    }

    /**
     * 保存并且发送短信验证码
     * @param smsDto
     */
    private void saveAndSend(SmsDto smsDto){
        //生成一个6位随机数
        String code = String.valueOf((int)(((Math.random() * 9 ) +1 )*100000));
        smsDto.setAt(new Date());
        smsDto.setStatus(SmsStatusEnum.NOT_USED.getCode());
        smsDto.setCode(code);
        this.save(smsDto);

        //调用短信通道

    }

    /**
     * 验证码5分钟内有效，且操作类型必须一致
     * @param smsDto
     */
    public void validCode(SmsDto smsDto){
        ResponseDto responseDto = new ResponseDto();
        SmsExample example = new SmsExample();
        SmsExample.Criteria criteria = example.createCriteria();
        //查找5分钟内同一个手机号同一种操作的发送记录
        criteria.andMobileEqualTo(smsDto.getMobile())
                .andUseEqualTo(smsDto.getUse())
                .andAtGreaterThan(new Date(new Date().getTime() - 5 * 60 * 1000));
        List<Sms> smsList = smsMapper.selectByExample(example);

        //判断结果，如果不为空
        if(smsList!=null && smsList.size()>0){
           Sms smsDb= smsList.get(0);
           //如果数据库的验证码和已发送的验证码不相符，表示验证码不正确
           if(!smsDb.getCode().equals(smsDto.getCode())){
               LOG.warn("短信验证码不正确");
               throw new  BusinessException(BusinessExceptionCode.MOBILE_CODE_ERROR);
           }else {
               //如果验证码正确，则修改当前手机号的状态为已使用
               smsDto.setStatus(SmsStatusEnum.USED.getCode());
               smsMapper.updateByPrimaryKey(smsDb);
           }
        }else{
            //如果结果查不到则从新发送短信
            LOG.warn("短信验证码不存在或者已过期，请重新发送短信");
            throw new BusinessException(BusinessExceptionCode.MOBILE_CODE_EXPIRED);
        }
    }


}