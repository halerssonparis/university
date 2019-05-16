$(document).ready(function () {
   
    controllerA.referenceState( PageManager.getState() );
    controllerA.setStateElement( 'h1', 10 );

    controllerA.readOnly(false);

    console.log(controllerA.getState())

    controllerA.hide(false)

    //controllerA.setNewFuncionality( 'h1', {say: say} );
    //controllerA.callElementFunction( {id: 'h1', functionName: 'say'} );

});


function* generator() {
    yield i;
}

var PageManager = (function() 
{
    
    var state = {
        head: {
            h1: loadElementState( 'h1' ),
            h2: loadElementState( 'h2' ),
            h3: loadElementState( 'h3' ),
        },
        body: {
            b1: loadElementState( 'b1' ),
            b2: loadElementState( 'b2' ),
            b2: loadElementState( 'b3' ),
        },
        footer: {
            f1: loadElementState( 'f1' ),
        }
    }

    function loadElementState( id ) 
    {
        return {
            id: id,
            historic: [],
            parents: _global.getParents( id ) || [],
            type: _global.getTypeElement( id ) || TYPE.INPUT,
            getValue: getValueOnElement( id, this.type ), 
            setValue: ( newValue ) => { setValueOnElement( id, newValue, this.type ) },
            setAttribute: function( attribute, confirm ) { setAttributeOnElement( id, attribute, confirm, this.type ) },
        }
    }

    function getValueOnElement( id, type )
    {
        var element = $("#" + id)

        switch (type) {
            case TYPE.INPUT:
                return element.val();
        
            case TYPE.OTHER:
                return element.text();

            default:
                break;
        }
    } 

    function setAttributeOnElement( id, attribute, confirm, typeElement )
    {
        var element = $("#" + id)
        confirm = typeof(confirm) == 'boolean' ? confirm : false;

        if ( attribute == ATTR.READONLY && typeElement == TYPE.INPUT ) 
        {
            element.attr('disabled', confirm);
            element.attr('readonly', confirm);
        } else 
        {
            element.attr(attribute, confirm);
        } 
    }

    function setValueOnElement( id, newValue )
    {
        $("#" + id).val( newValue );
    }


    function getState() 
    {
        return state;
    }

    function hide( id, confirm )
    {
        var element = $("#" + id);
        confirm = typeof(confirm) == 'boolean' ? confirm : true;

        if (confirm)
            element.hide();
        else
            element.show();
    }

    return {
        getState: getState,
        hide: hide
    }

})();


