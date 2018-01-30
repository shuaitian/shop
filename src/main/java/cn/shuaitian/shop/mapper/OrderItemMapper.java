package cn.shuaitian.shop.mapper;

import cn.shuaitian.shop.entity.OrderItem;
import cn.shuaitian.shop.entity.OrderItemExample;
import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Param;

public interface OrderItemMapper {
    long countByExample(OrderItemExample example);

    int deleteByExample(OrderItemExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(OrderItem record);

    int insertSelective(OrderItem record);

    List<OrderItem> selectByExample(OrderItemExample example);

    OrderItem selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") OrderItem record, @Param("example") OrderItemExample example);

    int updateByExample(@Param("record") OrderItem record, @Param("example") OrderItemExample example);

    int updateByPrimaryKeySelective(OrderItem record);

    int updateByPrimaryKey(OrderItem record);
    
    List<OrderItem> getByPage(@Param("userID")Integer userID,@Param("curPage")Integer curPage,@Param("itemCount") Integer itemCount);

	Integer getCountByUserID(@Param("userID")Integer userID);

	Set<Integer> getDistinctGoodIds();

	Integer getCountByGoodId(@Param("goodId") int goodId);
}