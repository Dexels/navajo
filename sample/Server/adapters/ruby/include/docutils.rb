include_class Java::com.dexels.navajo.document.NavajoFactory
include_class Java::com.dexels.navajo.client.NavajoClientFactory
include_class Java::java.lang.System


$navajoMap = nil
$client = nil
$navajoMap = Hash.new
$messageStack = Array.new
$messageStack.push($output);


def setupClient(url, username,password)
  $client = NavajoClientFactory.getClient() 
  $client.username = username
  $client.password = password
  $client.serverUrl = url
end

def call(service, input=nil)
  if(input==nil)
    input = NavajoFactory.getInstance().createNavajo;
  end
  result = $client.doSimpleSend(input,service)
  $navajoMap[service] = result;
  return result;
end


def setNavajo(service,navajo)
  
end


def push(current, pathElement)
  res = $messageStack.last.getMessage(pathElement);
  if(res==nil)
    res = NavajoFactory.getInstance().createMessage(current, pathElement);
    $messageStack.last.addMessage(res);
  end
  $messageStack.push(res);
end

def pop()
  $messageStack.pop
end

def getValue(current,path)
  prop = current.getProperty(path);
  return prop.getTypedValue
end

def setValue(current,name,value,type="string",description="",length=0)
  if(current == $messageStack.first)
    res = $messageStack.last.getProperty(name);
  else
    res = current.getProperty(name);
  end
  
  if(res==nil)
    res = NavajoFactory.getInstance().createProperty(current, name,"string",value,length,description,"dir_in");
    $messageStack.last.addProperty(res);
  end
  res.setType(type);
#  res.setValue(value);
  return res;
end

def addElement
  msg = $messageStack.last;
  # test for properties / submessages?
  msg.setType("array");
  res = NavajoFactory.getInstance().createMessage($messageStack.first, msg.name,"array_element");
  $messageStack.last.addMessage(res);
  res.setIndex(msg.getArraySize);
  $messageStack.push(res)
end

def addMethod(navajo,name)
    res = NavajoFactory.getInstance().createMethod(navajo,name,nil);
    navajo.addMethod(res);    
end

def addSelection(property, name, value, selected=false) 
  sel = NavajoFactory.getInstance().createSelection(property.getRootDoc,name,value,selected);
  property.addSelection(sel)
 end
