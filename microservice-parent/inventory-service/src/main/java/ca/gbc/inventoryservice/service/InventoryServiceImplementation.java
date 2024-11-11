package ca.gbc.inventoryservice.service;


import ca.gbc.inventoryservice.repostitory.InventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor

public class InventoryServiceImplementation implements InventoryService {

    private final InventoryRepository inventoryRepository;


    @Override
    public boolean isInStock(String skuCode, Integer quantity) {
        return inventoryRepository.existsBySkuCodeAndQuantityGreaterThanEqual(skuCode, quantity);
    }

}
