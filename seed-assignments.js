const mysql = require('mysql2/promise');

async function seedAssignments() {
  const connection = await mysql.createConnection({
    host: 'localhost',
    user: 'root',
    password: 'root',
    database: 'assetvault'
  });

  try {
    console.log('Checking for assets with assigned_user_id but no assignment record...');
    
    // Find assets that are assigned but have no ACTIVE assignment record
    const [assets] = await connection.execute(`
      SELECT a.id, a.assigned_user_id, a.assigned_date, a.status 
      FROM assets a
      LEFT JOIN asset_assignments aa ON a.id = aa.asset_id AND aa.status = 'ACTIVE'
      WHERE a.assigned_user_id IS NOT NULL AND aa.id IS NULL
    `);

    console.log(`Found ${assets.length} legacy assignments to migrate.`);

    for (const asset of assets) {
      const assignedDate = asset.assigned_date || new Date().toISOString().slice(0, 19).replace('T', ' ');
      
      await connection.execute(`
        INSERT INTO asset_assignments (asset_id, user_id, assigned_date, status)
        VALUES (?, ?, ?, 'ACTIVE')
      `, [asset.id, asset.assigned_user_id, assignedDate]);
      
      console.log(`Created assignment record for Asset ID: ${asset.id}`);
    }

    // Now let's assign some AVAILABLE assets randomly to populate the page
    const [availableAssets] = await connection.execute(`
      SELECT id FROM assets WHERE status = 'AVAILABLE' OR status = 'ACTIVE' LIMIT 15
    `);

    const [employees] = await connection.execute(`
      SELECT id FROM users LIMIT 10
    `);

    if (availableAssets.length > 0 && employees.length > 0) {
      console.log(`\nCreating mock assignments for ${availableAssets.length} available assets...`);
      let empIndex = 0;
      
      for (const asset of availableAssets) {
        const empId = employees[empIndex].id;
        const assignedDate = new Date();
        assignedDate.setDate(assignedDate.getDate() - Math.floor(Math.random() * 30)); // random date in last 30 days
        const formattedDate = assignedDate.toISOString().slice(0, 19).replace('T', ' ');

        // Create assignment
        await connection.execute(`
          INSERT INTO asset_assignments (asset_id, user_id, assigned_date, status)
          VALUES (?, ?, ?, 'ACTIVE')
        `, [asset.id, empId, formattedDate]);

        // Update asset
        await connection.execute(`
          UPDATE assets 
          SET status = 'ASSIGNED', assigned_user_id = ?, assigned_date = ?
          WHERE id = ?
        `, [empId, formattedDate, asset.id]);

        empIndex = (empIndex + 1) % employees.length;
      }
      console.log('Successfully created mock assignments!');
    } else {
      console.log('Not enough available assets or employees to create mock data.');
    }

  } catch (error) {
    console.error('Migration failed:', error);
  } finally {
    await connection.end();
  }
}

seedAssignments();
