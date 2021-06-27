// Exponential Search

mod search {
    use std::cmp;

    /// Find if the provided value is contained in the 
    /// provided vector of values via exponential search.
    ///
    // Complexity: O(log2(values.len()))
    ///
    /// The vector values is expected to be in sorted order.
    pub fn exponential_search(values: &Vec<i32>, value: i32) -> bool {
        if values.len() == 0 {
            return false;
        }
    
        if values[0] == value {
            return true;
        }

        let mut i: usize = 1;
        
        while i < values.len() && values[i] < value { 
            i = i * 2;
        }
    
        return binary_search(values, i / 2, cmp::min(i, values.len() - 1), value);
    }
    
    
    fn binary_search(values: &Vec<i32>, from: usize, to: usize, value: i32) -> bool {
        if values.len() == 0 {
            return false;
        }
    
        let mut left: usize = from;
        let mut right: usize = to;
        
        while left <= right {
            let mid: usize = (left + right) / 2;
            
            if values[mid] == value {
                return true;
            }
            
            if values[mid] < value {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        
        return false;
    }
}

#[cfg(test)]
mod tests {

    #[test]
    fn test_exponential_search() {
        assert!(crate::search::exponential_search(&vec![-2, 0, 2, 3, 7, 24], 3)); 
    }
    
    #[test]
    fn test_exponential_search_negative() {
        assert!(!crate::search::exponential_search(&vec![-2, 0, 2, 3, 7, 24], 4)); 
    }
}
