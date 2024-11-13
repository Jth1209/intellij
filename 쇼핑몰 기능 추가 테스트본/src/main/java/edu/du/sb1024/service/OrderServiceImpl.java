package edu.du.sb1024.service;

import edu.du.sb1024.common.FileUtils;
import edu.du.sb1024.common.FileUtilsForOrder;
import edu.du.sb1024.entity.BoardDto;
import edu.du.sb1024.entity.BoardFileDto;
import edu.du.sb1024.entity.OrderDto;
import edu.du.sb1024.entity.OrderFileDto;
import edu.du.sb1024.mapper.BoardMapper;
import edu.du.sb1024.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrdersService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private FileUtilsForOrder fileUtils;


    @Override
    public List<OrderDto> selectOrderList() throws Exception {
        return orderMapper.selectOrderList();
    }

    @Override
    public void insertOrder(OrderDto order, MultipartHttpServletRequest multipartHttpServletRequest) throws Exception {
        orderMapper.insertOrder(order);
        List<OrderFileDto> list = fileUtils.parseFileInfo(order.getId(), multipartHttpServletRequest);
        if (!CollectionUtils.isEmpty(list)) {
            orderMapper.insertOrderFileList(list);
        }
    }

    @Override
    public void updateOrder(OrderDto order) throws Exception {
        orderMapper.updateOrder(order);
    }

    @Override
    public void deleteOrder(int orderIdx) throws Exception {
        orderMapper.deleteOrder(orderIdx);
    }

    @Override
    public OrderFileDto selectOrderFileInformation(int idx, int orderIdx) throws Exception {
        return orderMapper.selectOrderFileInformation(idx,orderIdx);
    }

    @Override
    public List<OrderDto> configuration(String type, String keyword) throws Exception {
        List<OrderDto> list = new ArrayList<>();
        if (type.equals("all")) {
            if (keyword.equals("no")) {
                list = orderMapper.selectOrderList();
            } else {
                list = orderMapper.selectOrderListWithKeyword(keyword);
            }
        } else if (type.equals("aggi")) {
            if (keyword.equals("no")) {
                list = orderMapper.selectOrderListWithAggi(type);
            } else {
                list = orderMapper.selectOrderListWithKeywordAndAggi(keyword, type);
            }
        } else if (type.equals("etc")) {
            if (keyword.equals("no")) {
                list = orderMapper.selectOrderListWithEtc(type);
            } else {
                list = orderMapper.selectOrderListWithKeywordAndEtc(keyword, type);
            }
        }
        return list;
    }
}

