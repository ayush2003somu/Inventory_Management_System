        try{                                                         
            Class.forName("com.mysql.cj.jdbc.Driver");               
        }catch(ClassNotFoundException e){        
            System.out.println("not extablished:");                    
            System.out.println(e.getMessage());                      
        }    