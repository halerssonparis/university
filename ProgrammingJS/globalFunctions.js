const ATTR = {
    READONLY: 'READONLY'
}

const TYPE = {
    INPUT: 'INPUT',
    OTHER: 'OTHER'
}

const _global = (function() 
{
    function getTypeElement( id ) 
    {
        try {
            var tag = $("#" + id).prop("tagName") //document.getElementById(id).tagName || TYPE.INPUT 
            console.log(tag)
            return tag;
        } catch (error) {
            console.log(error)
            return TYPE.INPUT
        }
    }

    function getParents( id )
    {
        var parents = []
        $("#" + id).parents().each(function() 
        {
            var parent = $(this).attr("id");

            if ( parent )
                parents.push(parent);
        })

        return parents;
    }

    return {
        getTypeElement: getTypeElement,
        getParents: getParents
    }

})();