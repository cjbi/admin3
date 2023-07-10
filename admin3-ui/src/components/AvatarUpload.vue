<template>
  <div class="info-image" @click="showDialog">
    <el-avatar :size="100" :src="avatarImg"/>
    <span class="info-edit">
								<i class="el-icon-lx-camerafill"></i>
							</span>
  </div>
  <el-dialog title="裁剪图片" v-model="dialogVisible" width="600px">
    <vue-cropper
      ref="cropper"
      :src="avatarImg"
      :ready="cropImage"
      :zoom="cropImage"
      :cropmove="cropImage"
      style="width: 100%; height: 400px"
    ></vue-cropper>

    <template #footer>
				<span class="dialog-footer">
					<el-button class="crop-demo-btn" type="primary"
          >选择图片
						<input class="crop-input" type="file" name="image" accept="image/*" @change="setImage"/>
					</el-button>
					<el-button type="primary" @click="saveAvatar">上传并保存</el-button>
				</span>
    </template>
  </el-dialog>
</template>
<script setup lang="ts">
import VueCropper from 'vue-cropperjs';
import 'cropperjs/dist/cropper.css';
import {ref, watch} from "vue";
import {upload} from "../api/storage";

const props = defineProps(['imgSrc']);
const emits = defineEmits(['onSelect']);
const avatarImg = ref('avatar.jpg');
const cropImg = ref('avatar.jpg');

const dialogVisible = ref(false);
const cropper: any = ref();
const showDialog = () => {
  dialogVisible.value = true;
}

const setImage = (e: any) => {
  const file = e.target.files[0];
  if (!file.type.includes('image/')) {
    return;
  }
  const reader = new FileReader();
  reader.onload = (event: any) => {
    dialogVisible.value = true;
    avatarImg.value = event.target.result;
    cropper.value && cropper.value.replace(event.target.result);
  };
  reader.readAsDataURL(file);
}
const cropImage = () => {
  cropImg.value = cropper.value.getCroppedCanvas().toDataURL();
}
watch(() => props.imgSrc, () => {
  avatarImg.value = props.imgSrc;
  cropImg.value = props.imgSrc;
}, {
  deep: true,
  immediate: true
});
const saveAvatar = () => {
  avatarImg.value = cropImg.value;
  dialogVisible.value = false;
  fetch(cropImg.value)
    .then(response => response.blob())
    .then(blob => {
      const file = new File([blob], "avatar.png", {type: blob.type})
      upload({files: file}).then(res => {
        emits('onSelect', res.data[0]?.url);

      });
    });
};

</script>
<style scoped>

.info-image {
  position: relative;
  margin: auto;
  width: 100px;
  height: 100px;
  background: #f8f8f8;
  border: 1px solid #eee;
  border-radius: 50px;
  overflow: hidden;
}

.info-edit {
  display: flex;
  justify-content: center;
  align-items: center;
  position: absolute;
  left: 0;
  top: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.5);
  opacity: 0;
  transition: opacity 0.3s ease;
}

.info-edit i {
  color: #eee;
  font-size: 25px;
}

.info-image:hover .info-edit {
  opacity: 1;
}

.crop-demo-btn {
  position: relative;
}

.crop-input {
  position: absolute;
  width: 100px;
  height: 40px;
  left: 0;
  top: 0;
  opacity: 0;
  cursor: pointer;
}
</style>
