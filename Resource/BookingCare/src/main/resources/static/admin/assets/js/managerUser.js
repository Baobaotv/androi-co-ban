$(document)
		.ready(

				function() {
					/*
					 * (function(){ new FroalaEditor("#description",{ // Define
					 * new link styles. linkStyles: { class1: 'Class 1', class2:
					 * 'Class 2' } }) })() shortDescription
					 */
					(function() {
						const editorInstance = new FroalaEditor(
								'#description',
								{
									events : {
										'image.beforeUpload' : function(files) {
											const editor = this
											if (files.length) {
												var reader = new FileReader()
												reader.onload = function(e) {
													var result = e.target.result
													editor.image.insert(result,
															null, null,
															editor.image.get())
												}
												reader.readAsDataURL(files[0])
											}
											return false
										}
									}
								})
					})();
					(function() {
						const editorshortDescription = new FroalaEditor(
								'#shortDescription',
								{
									events : {
										'image.beforeUpload' : function(files) {
											const editor = this
											if (files.length) {
												var reader = new FileReader()
												reader.onload = function(e) {
													var result = e.target.result
													editor.image.insert(result,
															null, null,
															editor.image.get())
												}
												reader.readAsDataURL(files[0])
											}
											return false
										}
									}
								})
					})();
					
					(function () {
						  var curentPage=   parseInt($('#curentPage').val());
						
				        window.pagObj = $('#pagination').twbsPagination({
				        	totalPages: 10,
							visiblePages: 4,
							startPage: parseInt(curentPage),
				            onPageClick: function (event, page) {
				            	if(page!=parseInt($('#curentPage').val())){
									$('#page').val(page);
									var url= window.location.pathname;
									$('#formPagination').attr('action',url)
									$('#formPagination').submit();
				            	}
				            }
				            
				        });
				    })();
					
					$("#img").change(function(event) {
						if (this.files && this.files[0]) {
							var reader = new FileReader();
							reader.onload = function(e) {
								$('#showImage').attr('src', reader.result);
							}
							reader.readAsDataURL(this.files[0]);
						}
					});
					var count = 0;

					$("#addUser")
							.click(
									function() {

										if ($("#uploadUser").valid()) {
											event.preventDefault();

											// Get form
											var form = $('#uploadUser')[0];
											var urlpath = window.location.origin;
											var data = new FormData(form);
											$
													.ajax({
														url : urlpath
																+ "/admin/api/managerUser/add",
														type : "POST",
														enctype : 'multipart/form-data',
														data : data,
														processData : false,
														contentType : false,
														cache : false,
														success : function(
																result) {
															if (result) {
																success: $(
																		'#modal')
																		.modal(
																				'hide');

																complete: $(
																		'#ok')
																		.modal(
																				'show');

																setTimeout(
																		function() {
																			window.location
																					.replace(urlpath
																							+ "/admin/managerUser")
																		}, 1500);
															}
														},
														error : function(e) {
															if (e.status) {
																success: $(
																		'#modal')
																		.modal(
																				'hide');

																complete: $(
																		'#ok')
																		.modal(
																				'show');

																setTimeout(
																		function() {
																			window.location
																					.replace(urlpath
																							+ "/admin/managerUser")
																		}, 1500);
															} else {
																alert('???? c?? l???i x???y ra !');
															}
														}
													});
										} else {
											alert('B???n c???n ??i???n l???i th??ng tin')
										}
									});
					$('#uploadUser')
							.validate(
									{
										rules : {
											fullname : {
												required : true

											},
											username : {
												required : true

											},
											phoneNumber : {
												required : true,
												number : true,
												minlength : 10,
												maxlength : 10
											},
											location : {
												required : true
											},
											specializedId : {
												required : true
											},
											workTimeId : {
												required : true
											},
											roleName : {
												required : true
											},
											email : {
												required : true,
												email : true
											},
											hospitalId : {
												required : true
											},
											sex : {
												required : true
											},
											yearOfBirth : {
												required : true
											},
											password : {
												required : true
											},
											confirmPassword : {
												equalTo : "#password"
											}
										},
										messages : {
											fullname : {
												required : "T??n kh??ng ???????c ????? tr???ng"
											},
											username : {
												required : "T??i kho???n kh??ng ???????c ????? tr???ng"
											},
											roleName : {
												required : "Ch???c n??ng kh??ng ???????c ????? tr???ng"
											},
											phoneNumber : {
												required : "S??? ??i???n tho???i kh??ng ???????c ????? tr???ng ",
												number : "Ch??? ???????c ph??p nh???p ch??? s???"
											},
											email : {
												required : "Email kh??ng ???????c ????? tr???ng",
												email : "B???n ???? nh???p sai ?????nh d???ng email"
											},
											hospitalId : {
												required : "B???nh vi???n kh??ng ???????c ????? tr???ng"
											},
											workTimeId : {
												required : "B???n ch??a ch???n ca kh??m",

											},
											location : {
												required : "B???n ch??a ch???n ?????a ch???",
											},
											sex : {
												required : "Gi???i t??nh kh??ng ???????c ????? tr???ng "
											},
											yearOfBirth : {
												required : "Ng??y sinh kh??ng ???????c ????? tr???ng "
											},
											specializedId : {
												required : "Chuy??n khoa kh??ng ???????c ????? tr???ng "
											},
											password : {
												required : "M???t kh???u kh??ng ???????c ????? tr???ng"
											},
											confirmPassword : {
												equalTo : "M???t kh???u kh??ng tr??ng kh???p"
											},
										},

										submitHandler : function(form) {
											alert('oke');
										}
									});

					// checkAllCheckBox
					$("#checkAll").change(
							function() {
								$("input[name='checkOne']").not(this).prop(
										'checked', this.checked);
							});
					$("#roleName").change(function() {
						var value = $("#roleName").val();
						if (value != "ROLE_DOCTOR") {
							// alert("hi");
							$("#hospitalId").hide();
							$(".checkWorkTime").hide();
							$("#specializedId").hide();
							if (value == "ROLE_USER") {
								// $('#description').hide();
								$('#img').hide();
							}
						} else {
							$("#hospitalId").show();
							$(".checkWorkTime").show();
							$("#specializedId").show();
							// $('#description').show();
							$('#img').show();
						}

					});

					// checkAnyCheckBox
					var countChecked = function() {
						count = $("input[name='checkOne']:checked").length
						console.log(count);
						if (count < 1) {
							$("#btnAddUser").prop("disabled", false);
							$("#btnDelete").prop("disabled", true);
							$("#btnEdit").prop("disabled", true);
						} else {
							$("#btnAddUser").prop("disabled", true);
							$("#btnDelete").prop("disabled", false);
							if (count == 1) {
								$("#btnEdit").prop("disabled", false);
							} else {
								$("#btnEdit").prop("disabled", true);
							}
						}
					};
					
					var checkUsername= function(){
						$("#username").on("input", function(e) {
							$('#msg').hide();
							if ($('#username').val() == null || $('#username').val() == "") {
								$('#msg').show();
								$("#msg").html("Username is a required field.").css("color", "red");
								$('#addUser').hide();
							} else {
								$.ajax({
									type: 'post',
									url: "/user",
									data: JSON.stringify({username: $('#username').val()}),
									contentType: 'application/json; charset=utf-8',
									cache: false,
									beforeSend: function (f) {
										$('#msg').show();
										$('#msg').html('Checking...');
									},
									statusCode: {
									    500: function(xhr) {
									    	$('#msg').show();
									    	$("#msg").html("Username available").css("color", "green");
									    	$('#addUser').show();
									    }
									},
									success: function(msg) {
										$('#msg').show();
										if(msg !== null || msg !== "") {
											$("#msg").html("Username already taken").css("color", "red");
											$('#addUser').hide();
										} else {
											$("#msg").html("Username available").css("color", "green");
											$('#addUser').show();
										}
									},
									error: function(jqXHR, textStatus, errorThrown) {
										$('#msg').show();
										$("#msg").html(textStatus + " " + errorThrown).css("color", "red");
									}
								});
							}
						});
					}
					countChecked();

					$("input[type=checkbox]").on("click", countChecked);

					// editProduct
					$("#btnEdit").click(
							function(event) {
								event.preventDefault();

								var values = new Array();

								$.each($("input[name='checkOne']:checked")
										.closest("td").siblings("td"),
										function() {
											values.push($(this).text());
										});
								console.log(values);
								$('#id').val(values[0]);
								$('#categoryId').val(values[1]).change();
								$('#sizeId').val(values[2]).change();
								$('#nameProduct').val(values[3]);

								$('#codeProduct').val(values[4]);
								$('#image').val(values[5]);
								$('#price').val(values[6]);
								$('#amount').val(values[7]);
								$('#memo').val(values[8]);

								$("#addProduct").css("display", "none");
								$("#editProduct").css("display", "block");
							});

					$("#btnAddUser").click(function(event) {
						event.preventDefault();
						$("#addUser").css("display", "block");
						$("#editUser").css("display", "none");
						checkUsername();
						
					});

					$("#btnDelete").click(
							function(event) {
								event.preventDefault();
								var data = {};
								var urlpath = window.location.origin;
								var ids = $(
										'tbody input[name="checkOne"]:checked')
										.map(function() {
											return $(this).val();
										}).get();
								data['ids'] = ids;
								$.ajax({
									url : urlpath
											+ "/admin/api/managerUser/delete",
									type : "post",
									contentType : "application/json",
									data : JSON.stringify(data),
									cache : false,
									success : function(result) {
										alert("oke lunn");
										window.location.replace(urlpath
												+ "/admin/managerUser");

									},
									error : function(e) {
										alert('???? c?? l???i x???y ra !');
										window.location.replace(urlpath
												+ "/admin/managerUser");
									}
								});
							});

//					$("#editProduct")
//							.click(
//									function() {
//										event.preventDefault();
//
//										// Get form
//										var form = $('#fileUploadForm')[0];
//
//										var data = new FormData(form);
//										$
//												.ajax({
//													url : "/baitaplon/quan-tri/san-pham/sua-san-pham",
//													type : "post",
//													enctype : 'multipart/form-data',
//													data : data,
//													processData : false,
//													contentType : false,
//													cache : false,
//													success : function(result) {
//														if (result) {
//
//															$('#modal').modal(
//																	'toggle');
//
//															setTimeout(
//																	function() {
//																		window.location
//																				.replace("http://localhost:8080/baitaplon/quan-tri/san-pham")
//																	}, 1000);
//															$('#ok').modal(
//																	'show');
//
//														}
//													},
//													error : function(e) {
//														if (e.status) {
//															success: $('#modal')
//																	.modal(
//																			'hide');
//
//															complete: $('#ok')
//																	.modal(
//																			'show');
//
//															setTimeout(
//																	function() {
//																		window.location
//																				.replace("http://localhost:8080/baitaplon/quan-tri/san-pham")
//																	}, 1500);
//														} else {
//															alert('???? c?? l???i x???y ra !');
//														}
//													}
//												});
//									});
				});