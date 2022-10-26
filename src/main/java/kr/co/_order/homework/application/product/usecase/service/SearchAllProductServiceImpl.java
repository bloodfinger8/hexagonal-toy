package kr.co._order.homework.application.product.usecase.service;

import kr.co._order.homework.application.product.usecase.port.in.SearchAllProductUseCase;
import kr.co._order.homework.application.product.usecase.port.out.SearchAllProductPort;
import kr.co._order.homework.application.product.usecase.service.response.SearchAllProductRes;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SearchAllProductServiceImpl implements SearchAllProductUseCase {
    private final SearchAllProductPort searchAllProductPort;

    public SearchAllProductServiceImpl(SearchAllProductPort searchAllProductPort) {
        this.searchAllProductPort = searchAllProductPort;
    }

    @Override
    @Transactional(readOnly = true)
    public SearchAllProductRes searchAllProducts() {
        return SearchAllProductRes.from(searchAllProductPort.searchAllProducts());
    }
}
