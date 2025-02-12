<script setup>

</script>

<script>
	import {
		getAdminById,
		updateAdminInfo,
		uploadAdminAvatar,
		getAdminAvatar
	} from "@/api/getData.js";
	import {
		getStorage,
		setStorage,
		delStorage
	} from '@/utils/localStorage.js'; // 引入缓存操作方法
	export default {
		data() {
			return {
				isEditing: false,
				showConfirmPassword: false,
				confirmDialogVisible: false,
				headers: {},
				adminInfo: {
					adminId: 'admin01',
					password: '',
					confirmPassword: ''
				},
				adminAvatar: '',
				avatarUrl: '',
				//默认头像
				defaultAvatar: require('@/assets/default.png'),
				passwordPlaceholder: '',
				rules: {
					password: [{
							required: true,
							message: '请输入密码',
							trigger: 'blur'
						},
						{
							min: 8,
							max: 16,
							message: '长度在 8 到 16 个字符',
							trigger: 'blur'
						},
						{
							pattern: /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,16}$/,
							message: "密码必须同时包含字母和数字",
							trigger: "blur"
						}
					]
				}
			};
		},
		watch: {
			isEditing(newVal) {
				if (!newVal) {
					this.passwordPlaceholder = this.adminInfo.password ? '********' : '';// 设置一个占位符表示密码已输入
				}
			}
		},
		created () {
			this.checkLoginStatus();
		},
		methods: {
			checkLoginStatus() {
				const isLoggedIn = getStorage('adminId') != null;

				if (!isLoggedIn) {
					alert('您还没有登录，请先登录！');
					this.$router.push('/site/login');
				}
			},
			//获取管理员信息
			getAdminByIdTo() {
				const adminId = getStorage('adminId');
				//用于测试的
				// const adminId = this.adminInfo.adminId;
				getAdminById({
					adminId
				}).then((res) => {
					this.adminInfo = res.result;
				});
				// 获取管理员头像
				getAdminAvatar({
						adminId: this.adminInfo.adminId
					})
					.then((res) => {
						if (res) {
							const blob = new Blob([res], {
								type: 'image/png'
							});
							this.adminAvatar = URL.createObjectURL(blob);
						} else {
							console.log('No avatar found for this admin');
						}
					})
					.catch((error) => {
						console.error('获取管理员头像失败:', error);
						// this.$message.error('无法获取管理员头像');
					});
			},
			//验证两次密码是否一致
			validateConfirmPassword(rule, value, callback) {
				if (value !== this.adminInfo.password) {
					callback(new Error('两次输入的密码不一致'));
				} else {
					callback();
				}
			},

			// 打开确认修改对话框
			openConfirmDialog() {
				if (this.isEditing) {
					this.confirmDialogVisible = true;
				} else {
					this.isEditing = true;
				}
			},
			// 保存或修改
			confirmEdit() {
				if (this.isEditing) {
					// 保存操作
					this.$refs.ruleForm.validate((valid) => {
						if (valid) {
							// 提交表单数据
							this.updateAdminInfoTo()
								.then(() => {
									if (this.avatarUrl) {
										return this.uploadAvatarToServer(); // 在确认编辑时上传头像到后端
									} else {
										return Promise.resolve(); // 否则直接返回一个已解决的 Promise
									}
								})
								.then(() => {
									this.isEditing = false;
									this.confirmDialogVisible = false;
								})
								.catch((error) => {
									console.error('修改或上传头像失败:', error);
									this.$message.error('修改或上传头像失败');
									this.confirmDialogVisible = false;
								});
						} else {
							this.$message.error('输入有误，请检查表单');
							this.confirmDialogVisible = false;
							return false;
						}
					});
				} else {
					this.isEditing = !this.isEditing;
					this.confirmDialogVisible = false;
				}
			},
			// 修改信息提交
			updateAdminInfoTo() {
				const data = {
					...this.adminInfo
				};
				delete data.confirmPassword; // 移除确认密码字段

				return updateAdminInfo(data)
					.then((res) => {
						if (res.code === 1) {
							this.$message.success('信息修改成功');
						} else {
							this.$message.error('信息修改失败');
						}
					})
					.catch((error) => {
						this.$message.error('信息修改失败');
						console.error('信息修改失败:', error);
					});
			},
			// 上传头像
			uploadAvatarToServer() {
				if (!this.adminInfo.adminId || !this.adminAvatar) {
					this.$message.error('请选择头像文件');
					return Promise.reject('请选择头像文件');
				}

				const formData = new FormData();
				formData.append('adminId', this.adminInfo.adminId);
				formData.append('avatar', this.adminAvatar);

				return uploadAdminAvatar(formData)
					.then((res) => {
						if (res.code === 1) {
							this.$message.success('头像上传成功');
							// 更新原始头像URL为新上传的头像URL
							this.adminAvatar = this.avatarUrl;
							this.avatarUrl = ''; // 清空当前预览URL
						} else {
							this.$message.error(res.result);
							return Promise.reject(res.result);
						}
					})
					.catch((error) => {
						this.$message.error('头像上传失败');
						console.error('头像上传失败:', error);
						return Promise.reject(error);
					});
			},
			handleAvatarSuccess(file) {},
			beforeAvatarUpload(file) {
				this.volunteerAvatar = file;
				this.avatarUrl = URL.createObjectURL(file); // 生成图片预览URL
				return false; // 阻止默认的文件上传行为

				// const isJPG = file.type === 'image/jpeg' || file.type === 'image/png';
				// const isLt2M = file.size / 1024 / 1024 < 2;

				// if (!isJPG) {
				// 	this.$message.error('上传头像图片只能是 JPG/PNG 格式!');
				// }
				// if (!isLt2M) {
				// 	this.$message.error('上传头像图片大小不能超过 2MB!');
				// }
				// return isJPG && isLt2M;
			}
		},
		mounted() {
			this.getAdminByIdTo();
			// 初始化 passwordPlaceholder 为当前密码
			this.passwordPlaceholder = '********';
		}
	}
</script>

<template>
	<div>
		<el-card>
			<el-form :model="adminInfo" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
				<el-row>
					<el-col :span="14">
						<el-form-item label="账号" prop="id" :style="{width:'400px'}">
							<el-input v-model="adminInfo.adminId" :disabled="true"></el-input>
						</el-form-item>
						<el-form-item label="密码" prop="password" :style="{width:'600px'}">
							<el-input v-if="isEditing" v-model="adminInfo.password" :disabled="!isEditing"
								@focus="showConfirmPassword = true" type="password" show-password></el-input>
							<el-input v-else v-model="passwordPlaceholder" :disabled="true" type="password">
							</el-input>
						</el-form-item>
						<el-form-item label="确认密码" v-if="showConfirmPassword && isEditing" prop="confirmPassword"
							:style="{width:'600px'}">
							<el-input v-model="adminInfo.confirmPassword" :disabled="!isEditing" type="password"
								show-password></el-input>
						</el-form-item>
					</el-col>
					<el-col :span="10">
						<el-form-item prop="adminAvatar" :style="{width:'400px'}">
							<el-upload
								:class="['avatar-uploader', { 'no-border': avatarUrl || adminAvatar || !isEditing }]"
								:show-file-list="false" :on-success="handleAvatarSuccess" action="" :headers="headers"
								accept="image/*" :before-upload="beforeAvatarUpload" :disabled="!isEditing"
								:data="{ id: adminInfo.adminId }">
								<img v-if="avatarUrl || adminAvatar" :src="avatarUrl || adminAvatar" class="avatar"
									crossorigin="anonymous">
								<img v-else-if="!isEditing" :src="defaultAvatar" class="avatar"
									crossorigin="anonymous" />
								<div v-else class="avatar-uploader-icon">
									<div class="upload-icon-container" v-if="isEditing">
										<i class="el-icon-plus"></i>
										<span class="upload-text">点击上传头像</span>
									</div>
								</div>
							</el-upload>
						</el-form-item>
					</el-col>
				</el-row>

			</el-form>
			<div class="button-container">
				<el-button type="primary" @click="openConfirmDialog">{{ isEditing ? '保存' : '修改' }}</el-button>
			</div>

		</el-card>

		<!-- 确认修改对话框 -->
		<el-dialog title="确认修改" :visible.sync="confirmDialogVisible" width="30%">
			<span>确定要保存修改吗？</span>
			<span slot="footer" class="dialog-footer">
				<el-button @click="confirmDialogVisible = false">取消</el-button>
				<el-button type="primary" @click="confirmEdit">确定</el-button>
			</span>
		</el-dialog>

	</div>
</template>

<style scoped>
	.button-container {
		display: flex;
		justify-content: center;
	}

	.el-container {
		height: 100%;
	}

	.avatar-uploader>>>.el-upload {
		border: 1px dashed #d9d9d9;
		border-radius: 6px;
		cursor: pointer;
		overflow: hidden;
		position: relative;
		width: 100px;
		height: 100px;
		display: flex;
		justify-content: center;
		align-items: center;
	}

	.avatar-uploader.no-border>>>.el-upload {
		border: none;
	}

	.avatar-uploader>>>.el-upload input {
		display: none;
	}

	.avatar-uploader>>>.el-upload .avatar-uploader-icon {
		font-size: 25px;
		color: #8c939d;
	}

	.avatar {
		width: 100px;
		height: 100px;
		display: block;
	}

	.upload-text {
		font-size: 12px;
	}

	.upload-icon-container {
		display: flex;
		flex-direction: column;
		/* 垂直排列 */
		align-items: center;
		/* 水平居中对齐 */
	}
</style>