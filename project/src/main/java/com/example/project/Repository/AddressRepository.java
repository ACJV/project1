package com.example.project.Repository;

import com.example.project.Model.Address;
import com.example.project.Model.Vehicle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AddressRepository {
    @Autowired
    JdbcTemplate template;

    public List<Address> fetchAll() {
        String sql = "SELECT * FROM address";
        RowMapper<Address> rowMapper = new BeanPropertyRowMapper<>(Address.class);
        return template.query(sql, rowMapper);
    }

    public Address addAddress(Address a) {
        //In case the address already exists
        List<Address> addressList = findAllMatching(a);
        // At least one address matching
        if(addressList.size() > 0){
            //Address already exists - No need to add more of the same to the db.
            // Since we're not returning the address, we don't have to set the addressId.
        } else {
            // No addresses found in the db - Add the address to the db
            String sql = "INSERT INTO address (address, zip, city, country, distance) VALUES (?, ?, ?, ?, ?)";
            template.update(sql, a.getAddress(), a.getZip(), a.getCity(), a.getCountry(),a.getDistance());
        }
        return null;
    }
    // Finds all addresses that have the same fields with different Id's:
    public List<Address> findAllMatching (Address address){
        String sql = "SELECT * FROM address WHERE address = ? AND zip = ? AND country = ? AND city = ? AND distance = ?";
        RowMapper<Address> rowMapper = new BeanPropertyRowMapper<>(Address.class);
        List<Address> addressList = template.query(sql, rowMapper, address.getAddress(), address.getZip(), address.getCountry(), address.getCity(), address.getDistance());
        return addressList;
    }
    // Finds One address with known Id
    public Address findAddress(int id) {
        String sql = "SELECT * FROM address WHERE address_id = ?";
        RowMapper<Address> rowMapper = new BeanPropertyRowMapper<>(Address.class);
        Address a = template.queryForObject(sql, rowMapper, id);
        return a;
    }
    // Deletes address with known Id
    public Boolean deleteAddress(int id){
        String sql = "DELETE FROM address WHERE address_id = ?";
        return template.update(sql, id) < 0;
    }
    // Updates fields of address with known Id - Take out int Id when possible
    public Address updateAddress(int id, Address a){
        String sql = "UPDATE address SET address = ?, zip =?, city=?, country=?, distance=? WHERE address_id = ?";
        template.update(sql, a.getAddress(), a.getZip(), a.getCity(), a.getCountry(),a.getDistance(), a.getAddressId());
        return null;
    }

    public List<Address> findByKeyword(@Param("keyword") String keyword)
    {
        String sql = "SELECT * FROM address WHERE address LIKE '%' ? '%'";
        RowMapper<Address> rowMapper = new BeanPropertyRowMapper<>(Address.class);
        return template.query(sql, rowMapper, keyword);
    }
    public Address findAddressId (Address address){
        // Use findAllMatching to search for addresses with the same fields
        List<Address> addressList = findAllMatching(address);
        // Possibility of more than one Address being returned - possible through update address method
        // If list size of addresses found is more than one:
        if(addressList.size()>1){
            // Find the lowest addressId - Since the lower id was created before the higher.
            // Most likely another object referring to the lower value Id.
            int lowestId = findLowestId(addressList);
            // Delete Addresses that have higher value addressId - Might cause problems if we find more than one.
            for (int i = 0; i < addressList.size(); i++){
                if(addressList.get(i).getAddressId() != lowestId){
                    boolean deleted = deleteAddress(addressList.get(i).getAddressId());
                }
            }
        }
        // Using get(0) is based on the assumption that the last object in the list would be moved to index 0
        // - Not been tested thoroughly, will be before deployment.
        Address a = addressList.get(0);
        return a;
    }
    // Finds and returns lowest addressId value in the addressList.
    public int findLowestId (List<Address> addressList){
        int lowestId = addressList.get(0).getAddressId();
        for(int i = 1; i < addressList.size(); i++){
            if(lowestId > addressList.get(i).getAddressId()){
                lowestId = addressList.get(i).getAddressId();
            }
        }
        return lowestId;
    }

//----------------------------------------------------------------------------------------------------------------------
    // @Justé
//----------------------------------------------------------------------------------------------------------------------

    public int getDistanceFromId (int id) {
        String sql = "SELECT address.distance FROM address WHERE address.address_id = ?;";
        RowMapper<Integer> rowMapper = new BeanPropertyRowMapper<>(Integer.class);
        List<Integer> distanceL = template.query(sql, rowMapper, id);
        int distance = distanceL.get(0);
        return distance;
    }

}
