package dssd.global.furniture.backend.services;

import dssd.global.furniture.backend.model.Collection;
import dssd.global.furniture.backend.model.DistributionOrders;
import dssd.global.furniture.backend.model.Store;
import dssd.global.furniture.backend.repositories.DistributionOrderRepository;
import dssd.global.furniture.backend.services.interfaces.DistributionOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
public class DistributionOrderServiceImplementation implements DistributionOrderService {
    @Autowired
    DistributionOrderRepository distributionOrderRepository;
    @Transactional
    public DistributionOrders setDistributionOrder(Store store, Collection collection, Integer quantity) {
        return distributionOrderRepository.save(new DistributionOrders(collection, store, quantity));
    }
}
