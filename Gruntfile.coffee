###
This file is used to run the LESS Linter.
####
module.exports = (grunt) ->
    grunt.initConfig
        lesslint:
            src: ['biz.ui.apps/**/*.less','global.ui.apps/**/*.less','sc.ui.apps/**/*.less','!**/*bootstrap*','!**/*target*']
            options:
                formatters: [
                    id: 'lint-xml'
                    dest: 'lesslint.xml'
                ]
                csslint:
                    'format': 'lint-xml',
                    'exclude-list': '--exclude-list=raw-html/,biz.ui.apps/src/main/content/jcr_root/etc/designs/ewcmbiz/bootstrap/,biz.ui.apps/target,global.ui.apps/src/main/content/jcr_root/etc/designs/ewcm/clientlib/bootstrap/,global.ui.apps/target,sc.ui.apps/target/,sc.ui.apps/target/classes/etc/designs/ewcmsc/clientlib/css/animate.css,sc.ui.apps/src/main/content/jcr_root/etc/designs/ewcmsc/clientlib/css/animate.css,biz.ui.apps/src/main/content/jcr_root/etc/designs/ewcmbiz/clientlib/css/animate.css,node_modules/'

    grunt.loadNpmTasks('grunt-lesslint')
