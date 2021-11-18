package com.dragonappear.inha.api.controller.item;


import com.dragonappear.inha.api.controller.item.dto.ItemLikeApiDto;
import com.dragonappear.inha.api.returndto.MessageDto;
import com.dragonappear.inha.domain.item.Item;
import com.dragonappear.inha.domain.item.UserLikeItem;
import com.dragonappear.inha.domain.user.User;
import com.dragonappear.inha.service.item.ItemService;
import com.dragonappear.inha.service.item.UserLikeItemService;
import com.dragonappear.inha.service.user.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


import static com.dragonappear.inha.api.returndto.MessageDto.getMessage;

@Api(tags = {"아이템 좋아요 API"})
@RequiredArgsConstructor
@RestController
public class ItemLikeApiController {
    private final UserService userService;
    private final UserLikeItemService userLikeItemService;
    private final ItemService itemService;

    @ApiOperation(value = "아이템 좋아요 API", notes = "아이템 좋아요")
    @PostMapping("/api/v1/items/likes")
    public MessageDto itemLike( @RequestBody ItemLikeApiDto dto) {
        Item item = itemService.findByItemId(dto.getItemId());
        User user = userService.findOneById(dto.getUserId());

        UserLikeItem userLikeItem = userLikeItemService.findByUserIdAndItemId(dto.getUserId(), dto.getItemId());
        if (userLikeItem == null) {
            userLikeItemService.save(new UserLikeItem(item, user));
            return MessageDto.builder()
                    .message(getMessage("result", "like"))
                    .build();
        }
        userLikeItemService.delete(userLikeItem);
            return MessageDto.builder()
                    .message(getMessage("result", "cancel"))
                    .build();
    }

    @ApiOperation(value = "아이템 좋아요 조회 API", notes = "아이템 좋아요 조회")
    @PostMapping(value = "/api/v1/items/isLiked")
    public MessageDto cancelItemLike( @RequestBody ItemLikeApiDto dto) {
        UserLikeItem userLikeItem = userLikeItemService.findByUserIdAndItemId(dto.getUserId(), dto.getItemId());
        if (userLikeItem == null) {
            return MessageDto.builder()
                    .message(getMessage("isLiked", false))
                    .build();
        }
        return MessageDto.builder()
                .message(getMessage("isLiked", true))
                .build();
    }
}
