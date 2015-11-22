/*************************************************************************
 *
 * ADOBE CONFIDENTIAL
 * __________________
 *
 *  Copyright 2014 Adobe Systems Incorporated
 *  All Rights Reserved.
 *
 * NOTICE:  All information contained herein is, and remains
 * the property of Adobe Systems Incorporated and its suppliers,
 * if any.  The intellectual and technical concepts contained
 * herein are proprietary to Adobe Systems Incorporated and its
 * suppliers and may be covered by U.S. and Foreign Patents,
 * patents in process, and are protected by trade secret or copyright law.
 * Dissemination of this information or reproduction of this material
 * is strictly forbidden unless prior written permission is obtained
 * from Adobe Systems Incorporated.
 **************************************************************************/

/**
 * @package guidelib.util.GuideUtil
 * @version 0.0.1
 *
 * Created with IntelliJ IDEA.
 * User: bsirivel
 * Date: 29/11/13
 * Time: 2:52 PM
 * To change this template use File | Settings | File Templates.
 */
(function ($, guidelib) {
    var GuideUtil = guidelib.util.GuideUtil = {
        GUIDE_ITEM_CONTAINER_SUFFIX : "_guide-item-container",
        GUIDE_ITEM_NAV_CONTAINER_SUFFIX : "_guide-item-nav-container",
        GUIDE_ITEM_SUFFIX : "_guide-item",
        GUIDE_ITEM_NAV_SUFFIX : "_guide-item-nav",
        GUIDE_NODE_ID_SUFFIX : "__",
        GUIDE_VIEW_BIND_ATTR : "data-guide-view-bind",

        xfaExprMap: {
            "clickExp": "click",
            "enabledExp":null,
            "visibleExp":null
        },

        _globalUniqueId : (new Date()).getTime(),

        generateUID : function(){
            return "GUID" + (++this._globalUniqueId);
        },

        getId: function (myid) {
            return "#" + myid.replace(/(:|\.|\[|\])/g, "\\$1");
        },

        guideSomToId: function (som) {
            //Note: If you change the som versus id logic than make sure you also take care of GuideBridge.setFocus and
            //AuthoringUtils.setAuthoringFocus which unfortunately based on this specially(AuthoringUtils.setAuthoringFocus).
            if (som) {
                return ("" + som).replace(/\./g, "-");
            }
            return null;
        },

        modelElSelector: function (id) {
            var modelSelector = '['+ GuideUtil.GUIDE_VIEW_BIND_ATTR+']';
            if(id){
                modelSelector = '['+ GuideUtil.GUIDE_VIEW_BIND_ATTR+'="'+id+'"]';
            }
            return modelSelector;
        },

        relativeSom: function (relativeTo, fullSom) {
            if (!relativeTo || !fullSom) {
                return fullSom;
            }
            else {
                if (fullSom.indexOf("guide[0].") == 0)
                    fullSom = fullSom.substring(9);
                if (relativeTo.indexOf("guide[0].") == 0)
                    relativeTo = relativeTo.substring(9);

                if (fullSom.indexOf(relativeTo) == 0)
                    return fullSom.substring(relativeTo.length +1);
                else
                    return fullSom;
            }
        },

        evalSom: function (som, obj) {
            if(som && obj) {
                return som.split('.').reduce(
                    function (obj, i) {
                        return obj[i];
                    },
                    obj
                );
            }  else {
                return null;
            }
        },

        itemContainerSelector : function(id){
            return "#" + id + GuideUtil.GUIDE_ITEM_CONTAINER_SUFFIX;
        },

        itemNavContainerSelector : function(id){
            var navContainerId = id + GuideUtil.GUIDE_ITEM_NAV_CONTAINER_SUFFIX;
            return ("#" + navContainerId +", [data-guide-id=" + navContainerId +"]");
        },

        itemSelector : function(id){
            return "#" + id + GuideUtil.GUIDE_ITEM_SUFFIX;
        },

        itemNavSelector : function(id){
            var navId = id + GuideUtil.GUIDE_ITEM_NAV_SUFFIX;
            return ("#" + navId +", [data-guide-id=" + navId +"]");
        },

        summarySelector : function(id){
            return ".guideSummary[data-guide-id='" + id + "']";
        },

        getLocalizedMessage: function(category, message, snippets){
            var resolvedMessage = message;
            if(snippets){
                //resolve message with snippet
                resolvedMessage = resolvedMessage.replace(/{(\d+)}/g, function(match, number) {
                    return typeof snippets[number] != 'undefined'
                        ? snippets[number]
                        : match
                        ;
                });
            }
            var text = "";
            if (category) {
                text += " [" + category + "]";
            }
            text += "  " + resolvedMessage + "\r\n" ;
            return text;
        },

        elIdWithoutNodeIdSuffix : function(id){
            var nodeIdSuffix = GuideUtil.GUIDE_NODE_ID_SUFFIX;
            if(id && (id.indexOf(GuideUtil.GUIDE_NODE_ID_SUFFIX) == (id.length - nodeIdSuffix.length))){
                id = id.substring(0, (id.length - nodeIdSuffix.length)); // remove id suffix
                return id;
            }
            else{
                return "";
            }
        },

        loadCSS: function(filename){
            if(!filename){
                return;
            }
            //Needs to be loaded in both authoring and runtime so not using ClientLibraryManager. Need better way to do that
            var file = document.createElement("link");
            file.setAttribute("rel", "stylesheet");
            file.setAttribute("type", "text/css");
            file.setAttribute("href", filename);

            if (typeof file !== "undefined"){
                document.getElementsByTagName("head")[0].appendChild(file);
            }
        },

        // selector points to wrapper div since it has a unique id
        adjustElementHeight  : function(selector){
            var self = $("#" + selector).find(".guideFieldWidget"),
                maxHeight,
                clientHeight = self.css("height"),
                childrenTotalHeight = 0,
                parentHeight = 0,
                $child = null,
                clone = self.clone()[0],
                $clonedChild = null;

            // Create a dummy element to get the height
            $(clone).css({
                'visibility': 'hidden',
                'max-height': self.css("max-height"),
                'top' : '-2000px',
                'left': '-2000px',
                'position' : 'absolute',
                'height': (clientHeight !== "0px") ? clientHeight : "auto"
            }).appendTo('body');

            parentHeight = $(clone).height();
            self.children().each(function(index){
                $clonedChild = $(clone).children().eq(index);
                if($clonedChild.height() >= parentHeight && ($child === null)){
                    $child = $(this);
                } else {
                    childrenTotalHeight += $clonedChild.height();
                }
            });
            if($child && (parentHeight - childrenTotalHeight) > 0)
                $child.height((parentHeight - childrenTotalHeight) + "px");
            else if(parentHeight - childrenTotalHeight < 0){
                self.children().first().height(parentHeight - $clonedChild.height() + "px");
            }
            $(clone).remove();
        },

        initializeHelp: function () {
            var shortVisible, getId, tooltipContent;
            shortVisible = false;
            getId = function(myid){return guidelib.util.GuideUtil.getId(myid);};

            $('[data-guide-longDescription]').click(function () {
                var index, longDescId, shortDescId, fieldId, longVisible, longDescDiv, shortDescDiv, fieldModel, fieldView;
                longDescId = $(this).data('guide-longdescription');
                index = longDescId.indexOf('_guideFieldLongDescription');
                if (index === -1) {
                    index = longDescId.indexOf('_guidePanelLongDescription');
                }
                fieldId = $(this).data('guide-longdescription').substring(0, index);
                shortDescId = fieldId + '_guideFieldShortDescription';
                /* get the divs*/
                longDescDiv =  $(getId(longDescId));
                shortDescDiv = $(getId(shortDescId));
                longDescDiv.toggle();
                longVisible = longDescDiv.is(':visible');
                fieldModel =window.guideBridge._resolveId(fieldId);
                fieldView = window.guideBridge._guideView.getView(fieldModel.somExpression);
                /*hiding shortDesc since longDesc is visible */
                if (longVisible) {
                    shortDescDiv.hide();
                } else{
                    if(fieldView!=null) {
                        fieldView.setActive(null, true);
                    }
                    if ($(this).data('guide-alwaysshow')) {
                        shortDescDiv.show();
                    }
                }

                fieldView.visibleHelpElement = longVisible ? "longDescription" :   shortDescDiv.is(":visible") ? "shortDescription" : "none";

                fieldModel._triggerOnBridge("elementHelpShown", fieldModel, fieldView.visibleHelpElement, "", {
                    help: $(fieldModel[fieldView.visibleHelpElement]).html()
                });
            });

            tooltipContent = function(element) {
                var longVisible, alwaysShow, guideFieldNode, elem;
                guideFieldNode = $(element).parents('.guideFieldNode').eq(0);
                longVisible =  $(getId(guideFieldNode.attr('id') + '_guideFieldLongDescription')).is(':visible');
                alwaysShow = $(guideFieldNode.find('[data-guide-longDescription]')).data('guide-alwaysshow');
                elem = guideFieldNode.find('.short');
                if (elem !== undefined && elem !== null && !longVisible && !alwaysShow) {
                    return elem.html();
                }
                return '';
            };

			$('.guideFieldWidget').tooltip({
                title: function(){
                    var button = $(this).find('button'),
                        checkbox = $(this).parents('.guideCheckBoxItem').eq(0),
                        radiobutton = $(this).parents('.guideRadioButtonItem').eq(0);
                    if (!(button !== undefined && button.length > 0) && 
                        !(checkbox !== undefined && checkbox.length > 0) && 
                        !(radiobutton !== undefined && radiobutton.length > 0)) {
						return tooltipContent(this);
                    }
                    return '';
                },
                placement: 'bottom',
                container: 'body',
                html:true
            });


            $('button, .guideRadioButtonItem, .guideCheckBoxItem').tooltip({
                title: function(){
                    return tooltipContent(this);
                },
                placement: 'bottom',
                container: 'body',
                html:true
            });
        }

    }
})(window.jQuery, window.guidelib);

