var controllerA = (function()
{
    var state = {}
    var divFather = ""

    function referenceState( object ) 
    {
        state = object.head || null;
        divFather = "head"
    }

    function setStateElement( obj, newValue )
    {
        state[obj].setValue( newValue );
        state[obj].historic.push( newValue );
    }

    function hide( confirm, id ) 
    {
        PageManager.hide(id ? id : divFather, confirm)
    }

    function readOnly( confirm, id )
    {
        for (var element in (id) ? {state: id} : state) 
        {
            state[element].setAttribute( ATTR.READONLY, confirm )
        }
    }

    function setNewFuncionality( obj, newFunction ) //Must receive json with parameter ex: { getName: function() { code here } }
    {
        state[obj] = {...state[obj], ...newFunction }
    }

    function callElementFunction( object ) // object must have id, fuctionName and params, ex:  {id: 'h1', functionName: say, params: something}
    {
        state[object.id][object.functionName]( object.params )
    }

    function getState() 
    {
        return state;
    }

    return {
        referenceState: referenceState,
        setStateElement: setStateElement,
        setNewFuncionality: setNewFuncionality,
        callElementFunction: callElementFunction,
        hide: hide,
        readOnly: readOnly,
        getState: getState,
    }

})();