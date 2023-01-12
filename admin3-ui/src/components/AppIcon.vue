<style scoped lang="scss">
.item {
  margin: 8px 8px 0px 0px;
  border-radius: 2px;
  border: 1px solid #ccc;
  display: inline-block;
  width: 30px;
  height: 30px;
  text-align: center;

  i {
    margin-top: 8px;
  }
}

.item:hover {
  border-color: #409EFF;
  color: #409EFF;
}
</style>

<template>
  <div style="overflow-y:scroll;height: 120px;margin-top: 10px;">
    <span
        class="item"
        :style="{
        backgroundColor: item.selected ? '#2196f3' : 'transparent',
      }"
        v-for="(item, index) in icons"
        :key="index"
        @click="handleSelect(item.name)"
    >
      <el-icon size="14px" :style="{ color: item.selected ? '#fff' : 'inherit' }">
        <component :is="item.name"></component>
      </el-icon>
    </span>
  </div>
</template>

<script lang="ts">
import {defineComponent, ref, watch} from 'vue'

export const FIRST_ICON = 'Document'

export default defineComponent({
  name: 'AppIcon',
  props: ['selectedIcon', 'modelValue'],
  emits: ['update:modelValue', 'change'],
  setup(props, {emit}) {
    const icons = ref([
      {
        "name": "Document",
        "selected": true
      },
      {
        "name": "DocumentAdd",
        "selected": false
      },
      {
        "name": "Notebook",
        "selected": false
      },
      {
        "name": "Tickets",
        "selected": false
      },
      {
        "name": "Memo",
        "selected": false
      },
      {
        "name": "Collection",
        "selected": false
      },
      {
        "name": "Postcard",
        "selected": false
      },
      {
        "name": "ScaleToOriginal",
        "selected": false
      },
      {
        "name": "SetUp",
        "selected": false
      },
      {
        "name": "DocumentDelete",
        "selected": false
      },
      {
        "name": "DocumentChecked",
        "selected": false
      },
      {
        "name": "DataBoard",
        "selected": false
      },
      {
        "name": "DataAnalysis",
        "selected": false
      },
      {
        "name": "CopyDocument",
        "selected": false
      },
      {
        "name": "FolderChecked",
        "selected": false
      },
      {
        "name": "Files",
        "selected": false
      },
      {
        "name": "Folder",
        "selected": false
      },
      {
        "name": "FolderDelete",
        "selected": false
      },
      {
        "name": "FolderRemove",
        "selected": false
      },
      {
        "name": "FolderOpened",
        "selected": false
      },
      {
        "name": "DocumentCopy",
        "selected": false
      },
      {
        "name": "DocumentRemove",
        "selected": false
      },
      {
        "name": "FolderAdd",
        "selected": false
      },
      {
        "name": "FirstAidKit",
        "selected": false
      },
      {
        "name": "Reading",
        "selected": false
      },
      {
        "name": "DataLine",
        "selected": false
      },
      {
        "name": "Printer",
        "selected": false
      },
      {
        "name": "Calendar",
        "selected": false
      },
      {
        "name": "CreditCard",
        "selected": false
      },
      {
        "name": "Box",
        "selected": false
      },
      {
        "name": "Money",
        "selected": false
      },
      {
        "name": "Refrigerator",
        "selected": false
      },
      {
        "name": "Cpu",
        "selected": false
      },
      {
        "name": "Football",
        "selected": false
      },
      {
        "name": "Brush",
        "selected": false
      },
      {
        "name": "Suitcase",
        "selected": false
      },
      {
        "name": "SuitcaseLine",
        "selected": false
      },
      {
        "name": "Umbrella",
        "selected": false
      },
      {
        "name": "AlarmClock",
        "selected": false
      },
      {
        "name": "Medal",
        "selected": false
      },
      {
        "name": "GoldMedal",
        "selected": false
      },
      {
        "name": "Present",
        "selected": false
      },
      {
        "name": "Mouse",
        "selected": false
      },
      {
        "name": "Watch",
        "selected": false
      },
      {
        "name": "QuartzWatch",
        "selected": false
      },
      {
        "name": "Magnet",
        "selected": false
      },
      {
        "name": "Help",
        "selected": false
      },
      {
        "name": "Soccer",
        "selected": false
      },
      {
        "name": "ToiletPaper",
        "selected": false
      },
      {
        "name": "ReadingLamp",
        "selected": false
      },
      {
        "name": "Paperclip",
        "selected": false
      },
      {
        "name": "MagicStick",
        "selected": false
      },
      {
        "name": "Basketball",
        "selected": false
      },
      {
        "name": "Baseball",
        "selected": false
      },
      {
        "name": "Coin",
        "selected": false
      },
      {
        "name": "Goods",
        "selected": false
      },
      {
        "name": "Sell",
        "selected": false
      },
      {
        "name": "SoldOut",
        "selected": false
      },
      {
        "name": "Key",
        "selected": false
      },
      {
        "name": "ShoppingCart",
        "selected": false
      },
      {
        "name": "ShoppingCartFull",
        "selected": false
      },
      {
        "name": "ShoppingTrolley",
        "selected": false
      },
      {
        "name": "Phone",
        "selected": false
      },
      {
        "name": "Scissor",
        "selected": false
      },
      {
        "name": "Handbag",
        "selected": false
      },
      {
        "name": "ShoppingBag",
        "selected": false
      },
      {
        "name": "Trophy",
        "selected": false
      },
      {
        "name": "TrophyBase",
        "selected": false
      },
      {
        "name": "Stopwatch",
        "selected": false
      },
      {
        "name": "Timer",
        "selected": false
      },
      {
        "name": "CollectionTag",
        "selected": false
      },
      {
        "name": "TakeawayBox",
        "selected": false
      },
      {
        "name": "PriceTag",
        "selected": false
      },
      {
        "name": "Wallet",
        "selected": false
      }, {
        "name": "Aim",
        "selected": false
      },
      {
        "name": "Bell",
        "selected": false
      },
      {
        "name": "ChatDotRound",
        "selected": false
      },
      {
        "name": "ChatDotSquare",
        "selected": false
      },
      {
        "name": "ChatLineRound",
        "selected": false
      },
      {
        "name": "ChatLineSquare",
        "selected": false
      },
      {
        "name": "ChatRound",
        "selected": false
      },
      {
        "name": "ChatSquare",
        "selected": false
      },
      {
        "name": "Check",
        "selected": false
      },
      {
        "name": "CircleCheck",
        "selected": false
      },
      {
        "name": "CircleClose",
        "selected": false
      },
      {
        "name": "CirclePlus",
        "selected": false
      },
      {
        "name": "Clock",
        "selected": false
      },
      {
        "name": "Close",
        "selected": false
      },
      {
        "name": "CloseBold",
        "selected": false
      },
      {
        "name": "Compass",
        "selected": false
      },
      {
        "name": "Connection",
        "selected": false
      },
      {
        "name": "Crop",
        "selected": false
      },
      {
        "name": "Delete",
        "selected": false
      },
      {
        "name": "Discount",
        "selected": false
      },
      {
        "name": "Edit",
        "selected": false
      },
      {
        "name": "EditPen",
        "selected": false
      },
      {
        "name": "Female",
        "selected": false
      },
      {
        "name": "Filter",
        "selected": false
      },
      {
        "name": "Finished",
        "selected": false
      },
      {
        "name": "FullScreen",
        "selected": false
      },
      {
        "name": "Hide",
        "selected": false
      },
      {
        "name": "House",
        "selected": false
      },
      {
        "name": "Link",
        "selected": false
      },
      {
        "name": "Loading",
        "selected": false
      },
      {
        "name": "Lock",
        "selected": false
      },
      {
        "name": "Male",
        "selected": false
      },
      {
        "name": "Message",
        "selected": false
      },
      {
        "name": "MessageBox",
        "selected": false
      },
      {
        "name": "Minus",
        "selected": false
      },
      {
        "name": "More",
        "selected": false
      },
      {
        "name": "MuteNotification",
        "selected": false
      },
      {
        "name": "Notification",
        "selected": false
      },
      {
        "name": "Odometer",
        "selected": false
      },
      {
        "name": "Open",
        "selected": false
      },
      {
        "name": "Operation",
        "selected": false
      },
      {
        "name": "PieChart",
        "selected": false
      },
      {
        "name": "Plus",
        "selected": false
      },
      {
        "name": "Pointer",
        "selected": false
      },
      {
        "name": "Position",
        "selected": false
      },
      {
        "name": "Refresh",
        "selected": false
      },
      {
        "name": "RefreshLeft",
        "selected": false
      },
      {
        "name": "RefreshRight",
        "selected": false
      },
      {
        "name": "Remove",
        "selected": false
      },
      {
        "name": "Search",
        "selected": false
      },
      {
        "name": "Select",
        "selected": false
      },
      {
        "name": "SemiSelect",
        "selected": false
      },
      {
        "name": "Service",
        "selected": false
      },
      {
        "name": "Setting",
        "selected": false
      },
      {
        "name": "Star",
        "selected": false
      },
      {
        "name": "Switch",
        "selected": false
      },
      {
        "name": "SwitchButton",
        "selected": false
      },
      {
        "name": "TurnOff",
        "selected": false
      },
      {
        "name": "Unlock",
        "selected": false
      },
      {
        "name": "User",
        "selected": false
      },
      {
        "name": "View",
        "selected": false
      },
      {
        "name": "Warning",
        "selected": false
      },
      {
        "name": "ZoomOut",
        "selected": false
      },
      {name: 'Microphone', selected: false},
      {name: 'Mic', selected: false},
      {name: 'VideoPause', selected: false},
      {name: 'VideoCamera', selected: false},
      {name: 'VideoPlay', selected: false},
      {name: 'Headset', selected: false},
      {name: 'Monitor', selected: false},
      {name: 'Film', selected: false},
      {name: 'Camera', selected: false},
      {name: 'Picture', selected: false},
      {name: 'PictureRounded', selected: false},
      {name: 'Iphone', selected: false},
      {name: 'Cellphone', selected: false},
      {name: 'VideoCameraFilled', selected: false},
      {name: 'PictureFilled', selected: false}
    ])
    const selectedIcon = ref(FIRST_ICON)

    const handleSelect = (icon: string) => {
      if (!icon) {
        return;
      }
      const index = icons.value.findIndex((i) => i.selected)
      const index2 = icons.value.findIndex((i) => i.name === icon)
      icons.value[index].selected = false
      icons.value[index2].selected = true
      selectedIcon.value = icon
      emit('change', icon)
      emit('update:modelValue', icon)
    }

    watch(() => props.modelValue, (icon) => {
      handleSelect(icon);
    }, {
      deep: true,
      immediate: true
    });
    return {
      icons,
      handleSelect,
    }
  },
})
</script>
