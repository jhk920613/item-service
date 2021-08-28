package hello.itemservice.web.basic;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.PostConstruct;
import java.util.List;

@Controller
@RequestMapping("/basic/items")
public class BasicItemController {

    private final ItemRepository itemRepository;

    @Autowired
    public BasicItemController(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);

        return "basic/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable("itemId") Long itemId, Model model) {
        Item findItem = itemRepository.findById(itemId);

        model.addAttribute("item", findItem);

        return "basic/item";
    }

    @GetMapping("/add")
    public String addForm() {

        return "basic/addForm";
    }

    // @PostMapping("/add")
    public String addItemV1(@RequestParam String itemName, @RequestParam Integer price, @RequestParam Integer quantity, Model model) {
        Item item = new Item(itemName, price, quantity);
        Item savedItem = itemRepository.save(item);

        model.addAttribute("item", savedItem);

        return "basic/item";
    }

    // @PostMapping("/add")
    public String addItemV2(@ModelAttribute("item") Item item) {
        itemRepository.save(item);

        // @ModelAttribute 를 사용하면 밑의 부분이 자동으로 추가되므로 생략 가능, Model 도 생략
        // model.addAttribute("item", savedItem);

        return "basic/item";
    }

    // @PostMapping("/add")
    public String addItemV3(@ModelAttribute Item item) {
        itemRepository.save(item);

        // @ModelAttribute 를 사용하면 밑의 부분이 자동으로 추가되므로 생략 가능, Model 도 생략
        // model.addAttribute("item", savedItem);

        return "basic/item";
    }

    // @PostMapping("/add")
    public String addItemV4(Item item) {
        itemRepository.save(item);

        // @ModelAttribute 를 사용하면 밑의 부분이 자동으로 추가되므로 생략 가능, Model 도 생략
        // model.addAttribute("item", savedItem);

        return "basic/item";
    }

    // @PostMapping("/add")
    public String addItemV5(Item item) {
        Item savedItem = itemRepository.save(item);
        return "redirect:/basic/items/" + savedItem.getId();
    }

    @PostMapping("/add")
    public String addItemV6(Item item, RedirectAttributes redirectAttributes) {
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/basic/items/{itemId}";    // ?status=true
    }

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable("itemId") Long itemId, Model model) {
        Item findItem = itemRepository.findById(itemId);

        model.addAttribute("item", findItem);

        return "basic/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable("itemId") Long itemId, @ModelAttribute Item item) {
        itemRepository.update(itemId, item);
        return "redirect:/basic/items/{itemId}";
    }

    /**
     * 테스트용 데이터 추가
     */
    @PostConstruct
    public void init() {
        itemRepository.save(new Item("itemA", 20000, 10));
        itemRepository.save(new Item("itemB", 30000, 20));
    }
}
