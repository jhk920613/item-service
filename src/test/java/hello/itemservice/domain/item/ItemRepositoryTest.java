package hello.itemservice.domain.item;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ItemRepositoryTest {

    ItemRepository itemRepository = new ItemRepository();

    @BeforeEach
    void beforeEach() {
        itemRepository.clearStore();
    }

    @Test
    void save() {
        // given
        Item item = new Item("itemA", 2000, 3);

        // when
        Item saveItem = itemRepository.save(item);

        // then
        Item findItem = itemRepository.findById(saveItem.getId());
        assertThat(saveItem).isEqualTo(findItem);
    }

    @Test
    void findAll() {
        // given
        Item itemA = new Item("itemA", 2000, 3);
        Item itemB = new Item("itemB", 3000, 4);

        itemRepository.save(itemA);
        itemRepository.save(itemB);

        // when
        List<Item> findItems = itemRepository.findAll();

        // then
        assertThat(findItems.size()).isEqualTo(2);
        assertThat(findItems).contains(itemA, itemB);
    }

    @Test
    void updateItem() {
        // given
        Item itemA = new Item("itemA", 2000, 3);

        Item saveItem = itemRepository.save(itemA);
        Long itemId = saveItem.getId();

        // when
        Item updateParam = new Item("itemU", 3000, 4);
        itemRepository.update(itemId, updateParam);

        Item findItem = itemRepository.findById(itemId);

        // then
        assertThat(updateParam.getItemName()).isEqualTo(findItem.getItemName());
        assertThat(updateParam.getPrice()).isEqualTo(findItem.getPrice());
        assertThat(updateParam.getQuantity()).isEqualTo(findItem.getQuantity());
    }
}