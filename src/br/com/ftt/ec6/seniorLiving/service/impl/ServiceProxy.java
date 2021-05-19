package br.com.ftt.ec6.seniorLiving.service.impl;

public class ServiceProxy {

//	ExecutorService threadPool = Executors.newWorkStealingPool();
//	private Object obj;
//	private Object objectReceived;
//	private Exception exceptionReceived;
//	
//	public static Object newInstance(Object obj) {
//		 return java.lang.reflect.Proxy.newProxyInstance(obj.getClass().getClassLoader(), obj.getClass().getInterfaces(), new ServiceProxy(obj));
//	}
//	 
//	private ServiceProxy(Object obj) {
//		this.obj = obj;
//	}
//	
//	private void invokeMethod(Object arg0, Method m, Object[] args, final InfoCallBack infoCallBack) throws InterruptedException {
//		try {
//			Object object = m.invoke(obj, args);
//			infoCallBack.onSucess(object);
//		}catch(Exception e) {
//			infoCallBack.onFail(e);
//		}
//	}
//	
//	@Override
//	public Object invoke(Object arg0, Method m, Object[] args) throws Throwable {
//		invokeMethod(arg0, m, args, new InfoCallBack() {
//			@Override
//			public void onSucess(Object object) {
//				objectReceived = object;
//			}
//			
//			@Override
//			public void onFail(Exception exception) {
//				exceptionReceived = exception;
//			}
//		});
//		
//		if(exceptionReceived != null) {
//            if(exceptionReceived.getCause() instanceof AccommodationException) {
//            	throw new AccommodationException(exceptionReceived.getCause().getMessage());
//            }
//            
//            if(exceptionReceived.getCause() instanceof TypeException) {
//            	throw new TypeException(exceptionReceived.getCause().getMessage());
//            }
//            
//            if(exceptionReceived.getCause() instanceof ProductException) {
//            	throw new ProductException(exceptionReceived.getCause().getMessage());
//            }
//            
//            if(exceptionReceived.getCause() instanceof LoginException) {
//            	throw new LoginException(exceptionReceived.getCause().getMessage());
//            }
//            
//            if(exceptionReceived.getCause() instanceof RestHomeException) {
//            	throw new RestHomeException(exceptionReceived.getCause().getMessage());
//            }
//		}
//		
//		return objectReceived;
//		
//	}

}
