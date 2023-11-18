package dssd.global.furniture.backend.services.interfaces;

import dssd.global.furniture.backend.controllers.dtos.request.OrdersRequestDTO;
import dssd.global.furniture.backend.model.Collection;
import dssd.global.furniture.backend.model.DistributionOrders;
import dssd.global.furniture.backend.model.Store;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public interface DistributionOrderService {

    public DistributionOrders setDistributionOrder(Store store, Collection collection, Integer quantity);
}
