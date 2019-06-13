

<?php 

    /**
    * 
    */
    class Database
    {
        /**
         * Khai b�o bi?n k?t n?i
         * @var [type]
         */
        public $link;

        public function __construct()
        {
            $this->link = mysqli_connect("localhost","root","","bookingtable") or die ();
            mysqli_set_charset($this->link,"utf8");
        }

        

        /**
         * [insert description] h�m insert 
         * @param  $table
         * @param  array  $data  
         * @return integer
         */
        public function insert($table, array $data) //Dau vao la 1 array. Boi vi thuong ng ta se insert nhieu dong
        {
            //code
            $sql = "INSERT INTO {$table} ";
            $columns = implode(',', array_keys($data));
            $values  = "";
            $sql .= '(' . $columns . ')';
            foreach($data as $field => $value) {
                if(is_string($value)) {
                    $values .= "'". mysqli_real_escape_string($this->link,$value) ."',";
                } else {
                    $values .= mysqli_real_escape_string($this->link,$value) . ',';
                }
            }
            $values = substr($values, 0, -1);
            $sql .= " VALUES (" . $values . ')';
            // _debug($sql);die;
            mysqli_query($this->link, $sql) or die("Loi  query  insert ----" .mysqli_error($this->link));
            return mysqli_insert_id($this->link);
        }

        public function update($sql) 
        {
            mysqli_query($this->link, $sql) or die( "L?i truy v?n Update -- " .mysqli_error());

           // return mysqli_affected_rows($this->link);
            return 1;
        }
        public function updateview($sql)
        {
            $result = mysqli_query($this->link,$sql)  or die ("L?i update view " .mysqli_error($this->link));
            return mysqli_affected_rows($this->link);

        }
        public function countTable($table) //dem so ban ghi trong 1 table
        {
            $sql = "SELECT id FROM  {$table}";
            $result = mysqli_query($this->link, $sql) or die("Loi Truy Van countTable----" .mysqli_error($this->link));
            $num = mysqli_num_rows($result);
            return $num;
        }


        /**
         * [delete description] h�m delete
         * @param  $table      [description]
         * @param  array  $conditions [description]
         * @return integer             [description]
         */
        public function delete ($table , $idColumnName,$id ) //delete by id
        {
            $sql = "DELETE FROM {$table} WHERE $idColumnName = $id ";

            mysqli_query($this->link,$sql) or die (" L?i Truy V?n delete   --- " .mysqli_error($this->link));
            return 1;
        }

        /**
         * delete array 
         */
        
        public function deletewhere($table,$data = array()) //delete theo nhieu id
        {
            foreach ($data as $id)
            {
                $id = intval($id);
                $sql = "DELETE FROM {$table} WHERE id = $id ";
                mysqli_query($this->link,$sql) or die (" L?i Truy V?n delete   --- " .mysqli_error($this->link));
            }
            return true;
        }

        public function fetchsql( $sql ) //thuc hien 1 cau lenh select sql => k nen xai cai nay
        {
            $result = mysqli_query($this->link,$sql) or die("L?i  truy v?n sql " .mysqli_error($this->link));
            $data = [];
            if( $result)
            {
                while ($num = mysqli_fetch_assoc($result))
                {
                    $data[] = $num;
                }
            }
            return $data;
        } 

        public function fetchID($table , $id ) //lay ra 1 ban ghi theo id => nen dung
        {
            $sql = "SELECT * FROM {$table} WHERE id = $id ";
            $result = mysqli_query($this->link,$sql) or die("L?i  truy v?n fetchID " .mysqli_error($this->link));
            return mysqli_fetch_assoc($result);
        }

        public function fetchOne($table , $query)
        {
            $sql  = "SELECT * FROM {$table} WHERE ";
            $sql .= $query;
            $sql .= "LIMIT 1";
            $result = mysqli_query($this->link,$sql) or die("L?i  truy v?n fetchOne " .mysqli_error($this->link));
            return mysqli_fetch_assoc($result);
        }

        public function deletesql ($table ,  $sql ) //delete theo 1 dieu kien sql nao do
        {
            $sql = "DELETE FROM {$table} WHERE " .$sql;
            // _debug($sql);die;
            mysqli_query($this->link,$sql) or die (" L?i Truy V?n delete   --- " .mysqli_error($this->link));
            return mysqli_affected_rows($this->link);
        }

        

         public function fetchAll($table) //lay ra toan bo ban ghi cua 1 bang nao do
        {
            $sql = "SELECT * FROM {$table} WHERE 1" ;
            $result = mysqli_query($this->link,$sql) or die("L?i Truy V?n fetchAll " .mysqli_error($this->link));
            $data = [];
            if( $result)
            {
                while ($num = mysqli_fetch_assoc($result))
                {
                    $data[] = $num;
                }
            }
            return $data;
        }

    
        public  function fetchJones($table,$sql,$total = 1,$page,$row ,$pagi = true ) //phan trang
        {
            
            $data = [];

            if ($pagi == true )
            {
               
                $sotrang = ceil($total / $row);
                $start = ($page - 1 ) * $row ;
                $sql .= " LIMIT $start,$row ";
                $data = [ "page" => $sotrang];
              
               
                $result = mysqli_query($this->link,$sql) or die("L?i truy v?n fetchJone ---- " .mysqli_error($this->link));
            }
            else
            {
                $result = mysqli_query($this->link,$sql) or die("L?i truy v?n fetchJone ---- " .mysqli_error($this->link));
            }
            
            if( $result)
            {
                while ($num = mysqli_fetch_assoc($result))
                {
                    $data[] = $num;
                }
            }
            
            return $data;
        }
         public  function fetchJone($table,$sql ,$page = 0,$row ,$pagi = false )
        {
            
            $data = [];
            // _debug($sql);die;
            if ($pagi == true )
            {
                $total = $this->countTable($table);
                $sotrang = ceil($total / $row);
                $start = ($page - 1 ) * $row ;
                $sql .= " LIMIT $start,$row";
                $data = [ "page" => $sotrang];
               
                $result = mysqli_query($this->link,$sql) or die("L?i truy v?n fetchJone ---- " .mysqli_error($this->link));
            }
            else
            {
                $result = mysqli_query($this->link,$sql) or die("L?i truy v?n fetchJone ---- " .mysqli_error($this->link));
            }
            
            if( $result)
            {
                while ($num = mysqli_fetch_assoc($result))
                {
                    $data[] = $num;
                }
            }
            // _debug($data);
            return $data;
        }


  /*      public  function fetchJoneDetail($table , $sql ,$page = 0,$total ,$pagi )
        {
            $result = mysqli_query($this->link,$sql) or die("L?i truy v?n fetchJone ---- " .mysqli_error($this->link));

            $sotrang = ceil($total / $pagi);
            $start = ($page - 1 ) * $pagi ;
            $sql .= " LIMIT $start,$pagi";

            $result = mysqli_query($this->link , $sql);
            $data = [];
            $data = [ "page" => $sotrang];
            if( $result)
            {
                while ($num = mysqli_fetch_assoc($result))
                {
                    $data[] = $num;
                }
            }
            return $data;
        }*/

        public function total($sql)
        {
            $result = mysqli_query($this->link  , $sql);
            $tien = mysqli_fetch_assoc($result);
            return $tien;
        }
    }
   
?>