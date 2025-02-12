<template>  
	<div>
	<el-card >
  <el-form ref="videoForm" :model="videoForm" label-width="80px" :rules="rules" @submit.native.prevent="submitForm" style="margin: 2%; margin-right: 40%;">  
    <el-form-item label="视频标题" prop="videoName">  
      <el-input v-model="videoForm.videoName"></el-input>  
    </el-form-item>  
    <el-form-item label="发布时间" prop="postDate">  
      <el-date-picker  
        v-model="videoForm.postDate"  
        type="datetime"  
        placeholder="选择发布时间"  
        value-format="yyyy-MM-ddTHH:mm:ss"  
      ></el-date-picker>  
    </el-form-item>  
    <el-form-item label="内容介绍" prop="introduction">  
      <el-input type="textarea" v-model="videoForm.introduction"></el-input>  
    </el-form-item>
	<el-form-item label="视频类型" prop="tag">
	  <el-select v-model="videoForm.tag" placeholder="请选择视频类型">
	    <el-option  
	      v-for="tag in serviceTags"  
	      :key="tag.value"  
	      :label="tag.label"  
	      :value="tag.value">  
	    </el-option>
		</el-select> 
	</el-form-item>
	  <el-form-item label="视频文件" prop="file">
	    <el-upload  
	      class="upload-demo"  
	      action="#"   
	      :on-change="handleFileChange"  
	      :file-list="videoForm.fileList"  
	      :auto-upload="false" 
		  :on-remove="handleRemove"
	    >  
	      <el-button slot="trigger" size="small" type="primary">选取文件</el-button>  
	      <el-button style="margin-left: 10px;" size="small" type="success" @click="submitForm('videoForm')">上传到服务器</el-button>
	      <div slot="tip" class="el-upload__tip">只能上传视频文件</div>  
	    </el-upload>  
	  </el-form-item>
	  
  </el-form>
	</el-card>
</div>
</template>  
  
<script>  
import {uploadVideo} from '@/api/getData.js';
export default { 
	
  data() {  
    return {  
      videoForm: {  
        videoName: '',  
        postDate: '',  
        introduction: '',
		tag: '', // 用于存储选中的标签值
		fileList: [], // 用于存储已选择的文件
      }, 
	  rules:{
		  videoName: [
		    { required: true, message: '请输入视频标题', trigger: 'blur' }
		  ],
		  postDate: [
		    { required: true, message: '请选择发布时间', trigger: 'change' }
		  ],
		  introduction: [
		    { required: true, message: '请输入内容介绍', trigger: 'blur' }
		  ],
		  tag: [
		    { required: true, message: '请选择视频类型', trigger: 'blur' }
		  ],
		  file: [
		    { required: true, message: '请选择视频', trigger: 'change' }
		  ],
		  
	  },
      // fileList: [], // 用于存储已选择的文件 
	   serviceTags: [  
	     { value: '环保志愿服务', label: '环保志愿服务' },  
	     { value: '助老助残志愿服务', label: '助老助残志愿服务' },  
	     { value: '支教助学志愿服务', label: '支教助学志愿服务' },  
	     { value: '社区志愿服务', label: '社区志愿服务' },  
	     { value: '医疗救助志愿服务', label: '医疗救助志愿服务' },  
	     { value: '应急救援志愿服务', label: '应急救援志愿服务' },  
	     { value: '文化传承志愿服务', label: '文化传承志愿服务' },  
	     { value: '法律援助志愿服务', label: '法律援助志愿服务' },  
	     { value: '心理健康志愿服务', label: '心理健康志愿服务' },  
	     { value: '科技创新志愿服务', label: '科技创新志愿服务' }  
	   ]  
    };  
  },  
  methods: {  
    handleFileChange(file, fileList) {  
      // 这里可以添加一些文件类型校验的逻辑  
      if (!file.raw.type.startsWith('video/')) {  
        this.$message.error('只能上传视频文件！');  
        return false;  
      }  
      this.videoForm.fileList = fileList; // 更新已选择的文件列表  
    }, 
	resetForm() {
	  this.$refs['videoForm'].resetFields();
	  this.videoForm.fileList.splice(0, 1); // 移除指定索引的文件
	  },
	 handleRemove(file, fileList) {
	        console.log(file, fileList);
	      },
	async submitForm() {  
	  if (!this.videoForm.fileList.length || !this.videoForm.videoName || !this.videoForm.tag || !this.videoForm.introduction || !this.videoForm.postDate) {  
	    this.$message.warning('请填写所有必填项！');  
	    return;  
	  }  
	  
	  // 创建FormData并添加文件和数据  
	  const formData = new FormData();  
	  formData.append('file', this.videoForm.fileList[0].raw); // 假设只上传一个文件  
	  formData.append('videoName', this.videoForm.videoName);  
	  formData.append('postDate', this.videoForm.postDate);  
	  formData.append('introduction', this.videoForm.introduction);  
	  formData.append('tag', this.videoForm.tag);  
	  console.log(this.videoForm);   
	  
	  try {  
	    const res = await uploadVideo(formData); // 使用 await 等待 uploadVideo 完成  
	    if (res.code === 1) { 
			
	      this.$alert('视频上传成功', '提示', {
	                confirmButtonText: '确定',
	      		        });
		  this.resetForm();
		  // this.$refs['videoForm'].resetFields();
	    } else {  
	      this.$message.error(res.result);  
	      throw new Error(res.result); // 使用 throw 抛出错误，以便在 catch 块中捕获  
	    }  
	  } catch (error) { 
		  console.log(error)
		  console.error('视频上传失败:', error);
	    this.$message.error('视频上传失败');  
	      
	    // 这里不需要再返回 Promise.reject，因为 async 函数会自动将 catch 块中的错误转换为 Promise.reject  
	  }  
	  
	  // 注意：如果你需要在上传成功或失败后都执行某些操作，你应该将它们放在 try-catch 块之外  
	  // 或者在 finally 块中（但在这个例子中，我们没有这样的需求）  
	}
	  
  },  
};  
</script>

