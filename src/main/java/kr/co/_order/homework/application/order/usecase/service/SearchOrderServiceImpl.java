package kr.co._order.homework.application.order.usecase.service;

import kr.co._order.homework.application.order.usecase.port.in.SearchOrderUseCase;
import kr.co._order.homework.application.order.usecase.port.out.SearchOrderPort;
import kr.co._order.homework.application.order.usecase.service.response.SearchOrderRes;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SearchOrderServiceImpl implements SearchOrderUseCase {

    private final SearchOrderPort searchOrderPort;

    public SearchOrderServiceImpl(SearchOrderPort searchOrderPort) {
        this.searchOrderPort = searchOrderPort;
    }

    @Transactional(readOnly = true)
    @Override
    public SearchOrderRes searchOrder() {
        return SearchOrderRes.from(searchOrderPort.searchOrder());
    }
}
