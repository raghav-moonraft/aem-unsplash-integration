(function ($, ns, channel, window, undefined) {

    /**
     *
     * Dependencies
     *
     */

    var extendClass = ns.util.extendClass;
    var AssetDragAndDrop = ns.ui.assetFinder.AssetDragAndDrop;
    var dropController = ns.ui.dropController;

    /**
     *
     * Constants
     *
     */

    var NAME = "Images";

    /**
     *
     * Internals
     *
     */

    var UnsplashDragAndDrop = extendClass(AssetDragAndDrop, {});

    /**
     *
     * Hooks
     *
     */

    // Register to the d&d controller (the UnsplashDragAndDrop behavior will be called for Assets cards annotated with data-type="Images")
    dropController.register(NAME, new UnsplashDragAndDrop());

   UnsplashDragAndDrop.prototype.prepareProperties = function(event, dropTarget) {
           var properties = UnsplashDragAndDrop.super_.prepareProperties.apply(this, arguments);
           var imgNode = dropTarget.name.substring(0, dropTarget.name.lastIndexOf("/"));
           properties[imgNode + "/jcr:lastModified"] = null;
           properties[imgNode + "/jcr:lastModifiedBy"] = null;
           properties[imgNode + "/fileName"] = null;
           properties[imgNode + "/file@Delete"] = "true";
           properties[imgNode + "/author"] = event.origin.dataset.author;
           return properties
       }

}(jQuery, Granite.author, jQuery(document), this));
