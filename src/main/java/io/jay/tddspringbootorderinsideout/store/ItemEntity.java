package io.jay.tddspringbootorderinsideout.store;

import io.jay.tddspringbootorderinsideout.domain.Item;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ItemEntity {

    @Id
    private String id;
    private String name;

    public ItemEntity(Item item) {
        BeanUtils.copyProperties(item, this);
    }

    public Item toDomain() {
        Item item = new Item();
        BeanUtils.copyProperties(this, item);
        return item;
    }
}
