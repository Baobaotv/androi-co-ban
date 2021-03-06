$(document).ready(function() {
	$('#editLich').on('change', function() {
		if(this.checked){
			 $('#editLichKham').removeAttr('hidden');
//			 var lstWkOld=$('.workTimeIdOld');
//			 var lstWkNew=$('.checkWorkTime');
//			 lstWkNew.forEach(element =>{
//				 element.value();
//			 });
		
			$('input[name=workTimeIdOld]').each(function () {
				 var value= this.defaultValue;
				 $('input[name=workTimeId]').each(function () {
					 if(this.defaultValue==value){ this.checked = true;}
					});
				
				});
		}else{
			
			 $('input[name=workTimeId]').each(function () {
				 this.checked = false;
				});
			 $('#editLichKham').attr("hidden",true);
		}
		 
		});
		$('#editPassword').on('change', function() {
			if(this.checked){
				 $('#changePassword').removeAttr('hidden');
			}else{
				 $('#changePassword').attr("hidden",true);;
			}
			 
			});
	 const editorInstance = new FroalaEditor('#description', {
	        events: {
	          'image.beforeUpload': function (files) {
	            const editor = this
	            if (files.length) {
	              var reader = new FileReader()
	              reader.onload = function (e) {
	                var result = e.target.result
	                editor.image.insert(result, null, null, editor.image.get())
	              }
	              reader.readAsDataURL(files[0])
	            }
	            return false
	          }
	        }
	      })
	 
//	 shortDescription
	 
	 const editorShortDescription = new FroalaEditor('#shortDescription', {
	        events: {
	          'image.beforeUpload': function (files) {
	            const editor = this
	            if (files.length) {
	              var reader = new FileReader()
	              reader.onload = function (e) {
	                var result = e.target.result
	                editor.image.insert(result, null, null, editor.image.get())
	              }
	              reader.readAsDataURL(files[0])
	            }
	            return false
	          }
	        }
	      })
    
    var readURL = function(input) {
        if (input.files && input.files[0]) {
            var reader = new FileReader();

            reader.onload = function (e) {
                $('.avatar').attr('src', e.target.result);
            }
    
            reader.readAsDataURL(input.files[0]);
        }
    }
    

    $("#img").on('change', function(){
        readURL(this);
    });
    $('#updateProfile')
	.validate(
			{
				rules : {
					fullname : {
						required : true

					},
					phoneNumber : {
						required : true,
						number : true,
						minlength : 10,
						maxlength : 10
					},
					location:{
						required : true
					},
					sex:{
						required : true
					},
					specializedId:{
						required : true
					},
					yearOfBirth:{
						required : true
					},
					hospitalId:{
						required : true
					},
					email:{
						required : true,
						email : true
					},
					workTimeId:{
						required : true
					},
					password:{
						required : true
					},
					confirmPassword:{
						equalTo : "#password"
					}
					
				},
				messages : {
					fullname : {
						required : "T??n kh??ng ???????c ????? tr???ng"

					},
					phoneNumber : {
						required : "S??? ??i???n tho???i kh??ng ???????c ????? tr???ng",
						number : "B???n ch??? ???????c ph??p nh???p s???",
						minlength : "B???n ???? nh???p sai s??? ??i???n tho???i",
						maxlength : "B???n ???? nh???p sai s??? ??i???n tho???i"
					},
					location:{
						required : "?????a ch??? kh??ng ???????c ????? tr???ng"
					},
					sex:{
						required : "Gi???i t??nh kh??ng ???????c ????? tr???ng"
					},
					specializedId:{
						required : "B???n ch??a ch???n m???c n??y"
					},
					yearOfBirth:{
						required : "Ng??y sinh kh??ng ???????c ????? tr???ng"
					},
					hospitalId:{
						required : "B???n ch??a ch???n m???c n??y"
					},
					email: {
						required: "Email kh??ng ???????c ????? tr???ng",
						email : "B???n ???? nh???p sai ?????nh d???ng email"
					},
					workTimeId:{
						required : "B???n ch??a ch???n m???c n??y"
					},
					password:{
						required : "M???t kh???u kh??ng ???????c ????? tr???ng"
					},
					confirmPassword :{
						equalTo : "M???t kh???u kh??ng tr??ng kh???p"
					}
				},

				submitHandler : function(form) {
					alert('oke');
				}
			});
    $("#btnUpdateProfile")
	.click(
			function() {
				if ($("#updateProfile").valid()){
					var shortDescriptionHTML= editorShortDescription.html.get();
					var descriptionHTML= editorInstance.html.get();
					var stringDesctiption=$.trim(jQuery(descriptionHTML).text());
					var stringShortDescription=$.trim(jQuery(shortDescriptionHTML).text());
					if(stringDesctiption&&stringShortDescription){
						event.preventDefault();
						var lich=$('#editLich');
						if(!lich.checked){
							$('input[name=workTimeIdOld]').each(function () {
								 var value= this.defaultValue;
								 $('input[name=workTimeId]').each(function () {
									 if(this.defaultValue==value){ this.checked = true;}
									});
								});
						}
		
						// Get form
						var form = $('#updateProfile')[0];
						var urlpath=window.location.href;
						var data = new FormData(form);
						$
								.ajax({
									url : urlpath,
									type : "POST",
									enctype : 'multipart/form-data',
									data : data,
									processData : false,
									contentType : false,
									cache : false,
									success : function(e) {
										if (e) {
											alert('B???n ???? c???p nh???p th??nh c??ng');
											setTimeout(
													function() {
														window.location
																.replace(urlpath)
													}, 1500);
										}
									},
									error : function(e) {
										if (e) {
												alert('???? c?? l???i x???y ra !');
											setTimeout(
													function() {
														window.location
																.replace(urlpath)
													}, 1500);
										} else {
											alert('???? c?? l???i x???y ra !');
										}
									}
								});
					}else{
						
							if(!stringDesctiption){
								alert("B???n c???n nh???p l???i m?? t???");
							}if(!stringShortDescription){
								alert("B???n c???n nh???p l???i m?? t??? ng???n");
							}
						}
					}else{
						alert("B??n c???n nh???p ????? th??ng tin c???n thi???t")
					}
					

			});
     
});