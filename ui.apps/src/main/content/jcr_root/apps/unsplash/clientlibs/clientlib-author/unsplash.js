(function ($, $document, author) {
    var self = {},
        EAEM_AUDIO = 'Unsplash';

    var searchPath = self.searchRoot = "/bin/unsplash",
        imageServlet = '/bin/wcm/contentfinder/asset/view.html',
        itemResourceType = 'cq/gui/components/authoring/assetfinder/asset';

    self.loadAssets = function (query, lowerLimit, upperLimit) {

        var param = {
            '_dc': new Date().getTime(),
            'query': query,
            'mimeType': 'audio',
            'itemResourceType': itemResourceType,
            'page': upperLimit/20,
            '_charset_': 'utf-8'
        };

        return $.ajax({
            type: 'GET',
            dataType: 'html',
            url: searchPath,
            data: param
        });
    };

    self.setSearchPath = function (spath) {
        searchPath = spath;
    };

    author.ui.assetFinder.register(EAEM_AUDIO, self);
}(jQuery, jQuery(document), Granite.author));