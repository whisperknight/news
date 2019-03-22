/**
 * @license Copyright (c) 2003-2018, CKSource - Frederico Knabben. All rights reserved.
 * For licensing, see https://ckeditor.com/legal/ckeditor-oss-license
 */

CKEDITOR.editorConfig = function (config) {
    // 获取项目名
    var pathName = window.document.location.pathname;
    var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);

    // 配置点击图像点击上传到服务器的url
    config.image_previewText = ' ';
    config.filebrowserImageUploadUrl = projectName + '/imageUploadServlet';
};
