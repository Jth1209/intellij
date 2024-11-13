package edu.du.sb1024.mapper;

import edu.du.sb1024.entity.BoardDto;
import edu.du.sb1024.entity.BoardFileDto;
import edu.du.sb1024.entity.OrderDto;
import edu.du.sb1024.entity.OrderFileDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OrderMapper {
    List<OrderDto> selectOrderList() throws Exception;

    List<OrderDto> selectOrderListWithKeyword(String keyword) throws Exception;

    List<OrderDto> selectOrderListWithKeywordAndAggi(String keyword , String type) throws Exception;

    List<OrderDto> selectOrderListWithKeywordAndEtc(String keyword , String type) throws Exception;

    List<OrderDto> selectOrderListWithAggi(String type) throws Exception;

    List<OrderDto> selectOrderListWithEtc(String type) throws Exception;

    void insertOrder(OrderDto order) throws Exception;

    void updateOrder(OrderDto order) throws Exception;

    void deleteOrder(int orderIdx) throws Exception;

    void insertOrderFileList(List<OrderFileDto> list) throws Exception;

    List<OrderFileDto> selectOrderFileList(int orderIdx) throws Exception;

    OrderFileDto selectOrderFileInformation(@Param("idx") int idx, @Param("orderIdx" )int orderIdx);
}
