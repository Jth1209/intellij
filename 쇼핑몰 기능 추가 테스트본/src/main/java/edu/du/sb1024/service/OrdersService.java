package edu.du.sb1024.service;

import edu.du.sb1024.entity.BoardDto;
import edu.du.sb1024.entity.BoardFileDto;
import edu.du.sb1024.entity.OrderDto;
import edu.du.sb1024.entity.OrderFileDto;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.List;

public interface OrdersService {
    List<OrderDto> selectOrderList() throws Exception;

    void insertOrder(OrderDto order, MultipartHttpServletRequest multipartHttpServletRequest) throws Exception;

    void updateOrder(OrderDto order) throws Exception;

    void deleteOrder(int orderIdx) throws Exception;

    OrderFileDto selectOrderFileInformation(int idx, int orderIdx) throws Exception;

    List<OrderDto> configuration(String type , String keyword) throws Exception;
}
