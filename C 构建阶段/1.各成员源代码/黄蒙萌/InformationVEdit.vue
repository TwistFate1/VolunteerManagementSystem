<script setup>

</script>

<script>
	import {
		getVolunteerById,
		updateVolunteerInfo,
		uploadVolunteerAvatar,
		getVolunteerAvatar
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
				isEditingNation: false,
				showConfirmPassword: false,
				confirmDialogVisible: false,
				// uploadUrl: '/volunteers/upload/avatar',
				// uploadUrl: '',
				headers: {},
				volunteerInfo: {
					volunteerId: 'v001',
					name: '',
					password: '',
					confirmPassword: '',
					gender: '',
					nation: '',
					phone: '',
					email: '',
					identity: '',
					area: ''
					// avatar: ''
				},
				//头像单独处理
				volunteerAvatar: '',
				avatarUrl: '', // 存储图片预览URL的变量
				//默认头像
				defaultAvatar: require('@/assets/default.png'),
				passwordPlaceholder: '',
				rules: {
					name: [{
							required: true,
							message: '请输入姓名',
							trigger: 'blur'
						},
						{
							min: 2,
							max: 15,
							message: '长度在 2 到 15 个字符',
							trigger: 'blur'
						}
					],
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
					],
					confirmPassword: [{
						required: true,
						validator: this.validateConfirmPassword,
						trigger: 'blur'
					}],
					phone: [{
						required: true,
						validator: this.validatePhone,
						trigger: 'blur'
					}],
					gender: [{
						required: true,
						message: '请选择性别',
						trigger: 'change'
					}],
					identity: [{
						required: true,
						message: '请输入身份证号',
						trigger: 'change'
					}],
					nation: [{
						required: true,
						message: '请输入民族',
						trigger: 'change'
					}],
					email: [{
						message: '请输入邮箱',
						trigger: 'blur'
					}],
					area: [{
						message: '请选择地区',
						trigger: 'blur'
					}]
				}
			};
		},
		watch: {
			isEditing(newVal) {
				if (!newVal) {
					this.passwordPlaceholder = this.volunteerInfo.password ? '********' : ''; // 设置占位符表示密码已输入
				}
			}
		},
		created () {
			this.checkLoginStatus();
		},
		methods: {
			checkLoginStatus() {
				const isLoggedIn = getStorage('volunteerId') != null;

				if (!isLoggedIn) {
					alert('您还没有登录，请先登录！');
					this.$router.push('/site/login');
				}
			},

			// 获取志愿者信息
			getVolunteerByIdTo() {
				const volunteerId = getStorage('volunteerId');
				// const volunteerId = this.volunteerInfo.volunteerId;
				getVolunteerById({
					volunteerId
				}).then((res) => {
					this.volunteerInfo = res.result;
					if (res.result.nation == null) {
						this.isEditingNation = true;
					}
				});
				// 获取志愿者头像
				getVolunteerAvatar({
						volunteerId: this.volunteerInfo.volunteerId
					})
					.then((res) => {
						if (res) {
							const blob = new Blob([res], {
								type: 'image/png'
							});
							this.volunteerAvatar = URL.createObjectURL(blob);
						} else {
							console.log('No avatar found for this volunteer');
						}
					})
					.catch((error) => {
						console.error('获取志愿者头像失败:', error);
						// this.$message.error('无法获取志愿者头像');
					});

			},

			//验证两次密码是否一致
			validateConfirmPassword(rule, value, callback) {
				if (value !== this.volunteerInfo.password) {
					callback(new Error('两次输入的密码不一致'));
				} else {
					callback();
				}
			},
			// 验证电话是否为11位纯数字
			validatePhone(rule, value, callback) {
				const phonePattern = /^\d{11}$/;
				if (!phonePattern.test(value)) {
					callback(new Error('电话必须为11位纯数字'));
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
							this.updateVolunteerInfoTo()
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
			updateVolunteerInfoTo() {
				const data = {
					...this.volunteerInfo
				};
				delete data.confirmPassword; // 移除确认密码字段
				data.gender = (this.genderText === '男' ? '0' : '1'); // 转换为整数

				return updateVolunteerInfo(data)
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
				if (!this.volunteerInfo.volunteerId || !this.volunteerAvatar) {
					this.$message.error('请选择头像文件');
					return Promise.reject('请选择头像文件');
				}

				const formData = new FormData();
				formData.append('volunteerId', this.volunteerInfo.volunteerId);
				formData.append('avatar', this.volunteerAvatar);

				return uploadVolunteerAvatar(formData)
					.then((res) => {
						if (res.code === 1) {
							this.$message.success('头像上传成功');
							// 更新原始头像URL为新上传的头像URL
							this.volunteerAvatar = this.avatarUrl;
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

			handleAvatarSuccess(file) {

			},

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
			this.getVolunteerByIdTo();
			// 初始化 passwordPlaceholder 为当前密码
			this.passwordPlaceholder = '********';
		},
		computed: {
			genderText: {
				get() {
					return this.volunteerInfo.gender === '0' ? '男' : '女';
				},
				set(value) {
					this.volunteerInfo.gender = (value === '男' ? '0' : '1');
				}
			}
		}

	}
</script>

<template>
	<div>
		<el-card>
			<el-form :model="volunteerInfo" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
				<el-row>
					<el-col :span="14">
						<el-form-item label="账号" prop="id" :style="{width:'400px'}">
							<el-input v-model="volunteerInfo.volunteerId" :disabled="true"></el-input>
						</el-form-item>
						<el-form-item label="密码" prop="password" :style="{width:'600px'}">
							<el-input v-if="isEditing" v-model="volunteerInfo.password" :disabled="!isEditing"
								@focus="showConfirmPassword = true" type="password" show-password></el-input>
							<el-input v-else v-model="passwordPlaceholder" :disabled="true" type="password">
							</el-input>
						</el-form-item>
						<el-form-item label="确认密码" v-if="showConfirmPassword && isEditing" prop="confirmPassword"
							:style="{width:'600px'}">
							<el-input v-model="volunteerInfo.confirmPassword" :disabled="!isEditing" type="password"
								show-password></el-input>
						</el-form-item>
					</el-col>
					<el-col :span="10">
						<el-form-item prop="volunteerAvatar" :style="{ width: '400px' }">
							<el-upload
								:class="['avatar-uploader', { 'no-border': avatarUrl || volunteerAvatar || !isEditing}]"
								:show-file-list="false" :on-success="handleAvatarSuccess" :headers="headers"
								accept="image/*" :before-upload="beforeAvatarUpload" :disabled="!isEditing"
								:data="{ id: volunteerInfo.volunteerId }" action="">
								<img v-if="avatarUrl || volunteerAvatar" :src="avatarUrl || volunteerAvatar"
									class="avatar" crossorigin="anonymous" />
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

				<el-row>
					<el-col :span="12">
						<el-form-item label="姓名" prop="name">
							<el-input v-model="volunteerInfo.name" :disabled="!isEditing"></el-input>
						</el-form-item>
					</el-col>
					<el-col :span="12">
						<el-form-item label="性别" prop="gender">
							<el-select v-model="genderText" placeholder="请选择性别" :disabled="true">
								<el-option label="男" value="男"></el-option>
								<el-option label="女" value="女"></el-option>
							</el-select>
						</el-form-item>
					</el-col>
				</el-row>
				<el-row>
					<el-col :span="6">
						<el-form-item label="民族" prop="nation">
							<!-- <el-input v-model="volunteerInfo.nation" :disabled="!isEditing"></el-input> -->
							<el-select v-model="volunteerInfo.nation" placeholder="请选择民族"
								:disabled="!isEditingNation || !isEditing">
								<el-option label="汉族" value="汉族"></el-option>
								<el-option label="壮族" value="壮族"></el-option>
								<el-option label="满族" value="满族"></el-option>
								<el-option label="回族" value="回族"></el-option>
								<el-option label="苗族" value="苗族"></el-option>
								<el-option label="维吾尔族" value="维吾尔族"></el-option>
								<el-option label="土家族" value="土家族"></el-option>
								<el-option label="彝族" value="彝族"></el-option>
								<el-option label="蒙古族" value="蒙古族"></el-option>
								<el-option label="藏族" value="藏族"></el-option>
								<el-option label="布依族" value="布依族"></el-option>
								<el-option label="侗族" value="侗族"></el-option>
								<el-option label="瑶族" value="瑶族"></el-option>
								<el-option label="朝鲜族" value="朝鲜族"></el-option>
								<el-option label="白族" value="白族"></el-option>
								<el-option label="哈尼族" value="哈尼族"></el-option>
								<el-option label="哈萨克族" value="哈萨克族"></el-option>
								<el-option label="黎族" value="黎族"></el-option>
								<el-option label="傣族" value="傣族"></el-option>
								<el-option label="畲族" value="畲族"></el-option>
								<el-option label="仡佬族" value="仡佬族"></el-option>
								<el-option label="东乡族" value="东乡族"></el-option>
								<el-option label="高山族" value="高山族"></el-option>
								<el-option label="拉祜族" value="拉祜族"></el-option>
								<el-option label="水族" value="水族"></el-option>
								<el-option label="佤族" value="佤族"></el-option>
								<el-option label="纳西族" value="纳西族"></el-option>
								<el-option label="羌族" value="羌族"></el-option>
								<el-option label="土族" value="土族"></el-option>
								<el-option label="仫佬族" value="仫佬族"></el-option>
								<el-option label="锡伯族" value="锡伯族"></el-option>
								<el-option label="柯尔克孜族" value="柯尔克孜族"></el-option>
								<el-option label="达斡尔族" value="达斡尔族"></el-option>
								<el-option label="景颇族" value="景颇族"></el-option>
								<el-option label="毛南族" value="毛南族"></el-option>
								<el-option label="撒拉族" value="撒拉族"></el-option>
								<el-option label="布朗族" value="布朗族"></el-option>
								<el-option label="塔吉克族" value="塔吉克族"></el-option>
								<el-option label="阿昌族" value="阿昌族"></el-option>
								<el-option label="普米族" value="普米族"></el-option>
								<el-option label="鄂温克族" value="鄂温克族"></el-option>
								<el-option label="怒族" value="怒族"></el-option>
								<el-option label="京族" value="京族"></el-option>
								<el-option label="基诺族" value="基诺族"></el-option>
								<el-option label="德昂族" value="德昂族"></el-option>
								<el-option label="保安族" value="保安族"></el-option>
								<el-option label="俄罗斯族" value="俄罗斯族"></el-option>
								<el-option label="裕固族" value="裕固族"></el-option>
								<el-option label="乌孜别克族" value="乌孜别克族"></el-option>
								<el-option label="门巴族" value="门巴族"></el-option>
								<el-option label="鄂伦春族" value="鄂伦春族"></el-option>
								<el-option label="独龙族" value="独龙族"></el-option>
								<el-option label="塔塔尔族" value="塔塔尔族"></el-option>
								<el-option label="赫哲族" value="赫哲族"></el-option>
								<el-option label="珞巴族" value="珞巴族"></el-option>
							</el-select>
						</el-form-item>
					</el-col>
					<el-col :span="8">
						<el-form-item label="地区" prop="area">
							<el-select v-model="volunteerInfo.area" placeholder="请选择地区" :disabled="!isEditing">
								<el-option label="北京市" value="北京市"></el-option>
								<el-option label="天津市" value="天津市"></el-option>
								<el-option label="河北省" value="河北省"></el-option>
								<el-option label="山西省" value="山西省"></el-option>
								<el-option label="内蒙古自治区" value="内蒙古自治区"></el-option>
								<el-option label="辽宁省" value="辽宁省"></el-option>
								<el-option label="吉林省" value="吉林省"></el-option>
								<el-option label="黑龙江省" value="黑龙江省"></el-option>
								<el-option label="上海市" value="上海市"></el-option>
								<el-option label="江苏省" value="江苏省"></el-option>
								<el-option label="浙江省" value="浙江省"></el-option>
								<el-option label="安徽省" value="安徽省"></el-option>
								<el-option label="福建省" value="福建省"></el-option>
								<el-option label="江西省" value="江西省"></el-option>
								<el-option label="山东省" value="山东省"></el-option>
								<el-option label="河南省" value="河南省"></el-option>
								<el-option label="湖北省" value="湖北省"></el-option>
								<el-option label="湖南省" value="湖南省"></el-option>
								<el-option label="广东省" value="广东省"></el-option>
								<el-option label="广西壮族自治区" value="广西壮族自治区"></el-option>
								<el-option label="海南省" value="海南省"></el-option>
								<el-option label="重庆市" value="重庆市"></el-option>
								<el-option label="四川省" value="四川省"></el-option>
								<el-option label="贵州省" value="贵州省"></el-option>
								<el-option label="云南省" value="云南省"></el-option>
								<el-option label="西藏自治区" value="西藏自治区"></el-option>
								<el-option label="陕西省" value="陕西省"></el-option>
								<el-option label="甘肃省" value="甘肃省"></el-option>
								<el-option label="青海省" value="青海省"></el-option>
								<el-option label="宁夏回族自治区" value="宁夏回族自治区"></el-option>
								<el-option label="新疆维吾尔自治区" value="新疆维吾尔自治区"></el-option>
								<el-option label="香港特别行政区" value="香港特别行政区"></el-option>
								<el-option label="澳门特别行政区" value="澳门特别行政区"></el-option>
								<el-option label="台湾省" value="台湾省"></el-option>
							</el-select>
						</el-form-item>
					</el-col>
					<el-col :span="10">
						<el-form-item label="电话" prop="phone">
							<el-input v-model="volunteerInfo.phone" :disabled="!isEditing"></el-input>
						</el-form-item>
					</el-col>
				</el-row>
				<el-row>
					<el-col :span="12">
						<el-form-item label="身份证号" prop="identity">
							<el-input v-model="volunteerInfo.identity" :disabled="!isEditing"></el-input>
						</el-form-item>
					</el-col>
					<el-col :span="12">
						<el-form-item label="邮箱" prop="email">
							<el-input v-model="volunteerInfo.email" :disabled="!isEditing"></el-input>
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