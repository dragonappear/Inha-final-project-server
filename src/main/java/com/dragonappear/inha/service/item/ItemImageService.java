package com.dragonappear.inha.service.item;

import com.dragonappear.inha.domain.item.Item;
import com.dragonappear.inha.domain.item.ItemImage;
import com.dragonappear.inha.domain.value.Image;
import com.dragonappear.inha.repository.item.ItemImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ItemImageService {
    @Autowired ItemImageRepository itemImageRepository;

    @Transactional
    // 아이템이미지 추가
    public Long update(ItemImage image) {
        validateImage(image);
        return itemImageRepository.save(image).getId();
    }

    // 아이템이미지 전체조회 by 아이템아이디
    public List<ItemImage> findByItemId(Long itemId) {
        List<ItemImage> list = itemImageRepository.findByItemId(itemId);
        if (list.size()==0) {
            throw new IllegalArgumentException("아이템 이미지가 존재하지 않습니다.");
        }
        return list;
    }

    // 아이템이미지 단건 삭제
    public void deleteOne(ItemImage image) {
        Item item = image.getItem();
        if (item != null) {
            item.getItemImages().remove(image);
            itemImageRepository.delete(image.getId());
        }else{
            throw new IllegalArgumentException("이미지에 어떠한 아이템과 연결되어있지 않습니다.");
        }
    }

    /**
     * 검증로직
     */

    private void validateImage(ItemImage image) {
        List<ItemImage> images = itemImageRepository.findByItemId(image.getItem().getId());
        for (ItemImage itemImage : images) {
            if (itemImage.getItemImage().getFileOriName() == image.getItemImage().getFileOriName()) {
                throw new IllegalArgumentException("이미 등록된 이미지입니다.");
            }
        }
    }
}
