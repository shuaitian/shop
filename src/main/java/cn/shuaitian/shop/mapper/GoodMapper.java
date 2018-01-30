package cn.shuaitian.shop.mapper;

import cn.shuaitian.shop.entity.Good;
import cn.shuaitian.shop.entity.GoodExample;
import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Param;

public interface GoodMapper {
    long countByExample(GoodExample example);

    int deleteByExample(GoodExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Good record);

    int insertSelective(Good record);

    List<Good> selectByExample(GoodExample example);

    Good selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Good record, @Param("example") GoodExample example);

    int updateByExample(@Param("record") Good record, @Param("example") GoodExample example);

    int updateByPrimaryKeySelective(Good record);

    int updateByPrimaryKey(Good record);
    
    List<Good> selectByPage(@Param("curPage") int curPage,@Param("itemCount") int itemCount);
    
    int selectPrice(Integer id);

	List<Good> getGoodsWithoutUserID(@Param("curPage") int curPage, @Param("itemCount")int itemCount, @Param("userID")int userID);

	Set<Integer> getGoodsWitUserID(@Param("userID")int userID);
}