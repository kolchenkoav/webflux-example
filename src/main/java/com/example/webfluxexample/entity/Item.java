package com.example.webfluxexample.entity;

import com.example.webfluxexample.model.ItemModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collation = "items")
public class Item {
    @Id
    private String id;
    private String name;
    private Integer count;

    @Field("subItems")
    private List<SubItem> subItems;

    public static Item from(ItemModel model) {
        var item = new Item();
        item.setName(model.getName());
        item.setCount(model.getCount());
        if (model.getSubItems() != null) {
            item.setSubItems(model.getSubItems().stream()
                    .map(SubItem::from)
                    .collect(Collectors.toList()));
        }
        return item;
    }
}
