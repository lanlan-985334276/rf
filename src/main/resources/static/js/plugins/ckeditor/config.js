/**
 * @license Copyright (c) 2003-2017, CKSource - Frederico Knabben. All rights reserved.
 * For licensing, see LICENSE.md or http://ckeditor.com/license
 */

CKEDITOR.editorConfig = function( config ) {
	// Define changes to default configuration here.
	// For complete reference see:
	// http://docs.ckeditor.com/#!/api/CKEDITOR.config

	// The toolbar groups arrangement, optimized for two toolbar rows.
	config.toolbarGroups = [
		{ name: 'clipboard',   groups: [ 'clipboard', 'undo' ] },
		{ name: 'editing',     groups: [ 'find', 'selection', 'spellchecker' ] },
		{ name: 'links' },
		{ name: 'insert' },
		{ name: 'forms' },
		{ name: 'tools' },
		{ name: 'document',	   groups: [ 'mode', 'document', 'doctools' ] },
		{ name: 'others' },
		'/',
		{ name: 'basicstyles', groups: [ 'basicstyles', 'cleanup' ] },
		{ name: 'paragraph',   groups: [ 'list', 'indent', 'blocks', 'align', 'bidi' ] },
		{ name: 'styles' },
		{ name: 'colors' },
		{ name: 'about' }
	];

	// Remove some buttons provided by the standard plugins, which are
	// not needed in the Standard(s) toolbar.
	config.removeButtons = 'Underline,Subscript,Superscript';

	// Set the most common block elements.
	config.format_tags = 'p;h1;h2;h3;pre';

	// Simplify the dialog windows.
	config.removeDialogTabs = 'image:advanced;link:advanced';

	config.width = "100"; //文本域宽度
	config.height = "200";//文本域高度

	config.image_previewText=' '; //预览区域显示内容

	//获取项目根路径
	var pathName = window.location.pathname.substring(1);
	var webName = pathName == '' ? '' : pathName.substring(0, pathName.indexOf('/'));
	var path="";
	if (webName == "") {
		path=path+ window.location.protocol + '//' + window.location.host;
	}
	else {
		path=path+  window.location.protocol + '//' + window.location.host + '/' + webName;
	}

	config.filebrowserUploadUrl=path+ "/upload/img"; //待会要上传的action或servlet
	config.filebrowserImageUploadUrl =path+ '/upload/img';
	config.filebrowserBrowseUrl =path+ "/imageUpload?action=fileBrowse";//这里的路径相对于根目录
	config.filebrowserImageBrowseUrl = path+ '/imageUpload?action=fileBrowse&type=Images';
};
