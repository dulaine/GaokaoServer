package com.baizhou.core.service.impl;

import com.baizhou.core.dao.*;
import com.baizhou.core.model.entity.*;
import com.baizhou.core.model.vo.*;
import com.baizhou.core.service.*;
import com.baizhou.util.BeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import javax.persistence.criteria.Predicate;
import org.springframework.util.StringUtils;
import java.util.*;


@Service
public class ClientInfoServiceImpl  implements ClientInfoService {


    @Autowired
    private ClientInfoRepo imp;
    /**
     * 保存
     * @param clientInfo
     * @return
     */
    @Override
    public ClientInfo saveClientInfo(ClientInfoVO clientInfo) {

        return imp.save(BeanMapper.map(clientInfo, ClientInfo.class));
    }
    /**
     * 删除单条记录
     * @param tel
     * @return
     */
    @Override
    public boolean deleteByKeyTel(String tel) {
        ClientInfo entity = imp.findByTel(tel).orElse(null);
        if(entity == null) return false;
        imp.delete(entity);
        return true ;
    }
    /**
     * 删除所有记录
     * @return
     */
    @Override
    public void deleteAll() {
        imp.deleteAll();
    }

    /**
     * 查询单条记录
     * @param tel
     * @return
     */
    @Override
    public ClientInfoVO findOneByTel(String tel) {
        ClientInfo entity = imp.findByTel(tel).orElse(null);
        if(entity == null) return null;
        return  BeanMapper.map(entity, ClientInfoVO.class);
    }

    @Override
    public  List<ClientInfoVO> findOneByTelLike(String tel) {
//        ClientInfo entity = imp.findByTelLike(tel)
//        if(entity == null) return null;
//        return  BeanMapper.map(entity, ClientInfoVO.class);
//

        List<ClientInfoVO> voList = new ArrayList<>();
        List<ClientInfo> inList = imp.findByTelLike("%"+tel+"%");
        if(  inList != null){
            for (ClientInfo inf:inList) {
                voList.add(BeanMapper.map(inf,ClientInfoVO.class));
            }
        }
        return  voList;


    }


    /**
    获取所有信息
     */
    @Override
    public List<ClientInfoVO> getAllClientInfos() {
        return BeanMapper.mapList(imp.findAll(), ClientInfoVO.class);
    }
    /**
     * 更新记录
     * @param clientInfoVO
     * @return
     */
    @Override
    public String updateClientInfo(ClientInfoVO clientInfoVO) {
        ClientInfo clientInfo = BeanMapper.map(clientInfoVO, ClientInfo.class);
        imp.save(clientInfo);
        return "success";
    }

     /**
     *  通过Name的like查询
     * @param name
     * @return
     */
    @Override
  public List<ClientInfoVO> findByName(String name){
        List<ClientInfoVO> voList = new ArrayList<>();
        List<ClientInfo> inList = imp.findByName(name);
        if(  inList != null){
            for (ClientInfo inf:inList) {
                voList.add(BeanMapper.map(inf,ClientInfoVO.class));
            }
        }
        return  voList;
  }

     /**
     *  通过Name的like查询
     * @param name
     * @return
     */
    @Override
  public List<ClientInfoVO> findByNameLike(String name){
        List<ClientInfoVO> voList = new ArrayList<>();
        List<ClientInfo> inList = imp.findByNameLike("%"+name+"%");
        if(  inList != null){
            for (ClientInfo inf:inList) {
                voList.add(BeanMapper.map(inf,ClientInfoVO.class));
            }
        }
        return  voList;
  }

    /**
     * 查询页面信息
     * @param pageable
     * @return
     */
    @Override
    public Map<String, Object> listbyPage(String tel,String name,Pageable pageable) {
        Map<String, Object> data = new HashMap<>();
        org.springframework.data.domain.Page<ClientInfo> pageResult = null;
        if (StringUtils.isEmpty(tel)  && StringUtils.isEmpty(name) ) {
            pageResult = imp.findAll(pageable);
        } else {
            List<Predicate> predicates = new ArrayList<>();
            Specification<ClientInfo> spec = (Specification<ClientInfo>) (root, query, cb) -> {
                    
                if (!StringUtils.isEmpty(tel)) {
                   String[] telSplits = tel.split("\\|");
                    Predicate p = null;
                    for (int i = 0; i < telSplits.length; i++) {
                        if(p == null){
                            p = cb.like(root.get("tel"), "%" + telSplits[i] + "%");
                        }else {
                            p = cb.or(p, cb.like(root.get("tel"), "%" + telSplits[i] + "%"));
                        }
                    }
                    predicates.add(p);
                }

                if (!StringUtils.isEmpty(name)) {
                   String[] nameSplits = name.split("\\|");
                    Predicate p = null;
                    for (int i = 0; i < nameSplits.length; i++) {
                        if(p == null){
                            p = cb.like(root.get("name"), "%" + nameSplits[i] + "%");
                        }else {
                            p = cb.or(p, cb.like(root.get("name"), "%" + nameSplits[i] + "%"));
                        }
                    }
                    predicates.add(p);
                }

                
                return cb.and(predicates.toArray(new Predicate[]{}));
            };
             pageResult = imp.findAll(spec, pageable);
        }

        List<ClientInfoVO> list = BeanMapper.mapList(pageResult.getContent(), ClientInfoVO.class);
        List<com.msg.ClientInfo> protoList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            protoList.add(list.get(i).ConvertToDTO());
        }
        data.put("items", protoList);
        data.put("total", pageResult.getTotalElements());////所有信息总数
        data.put("totalPage", pageResult.getTotalPages());//所有page总页数
        return data;
    }
}
