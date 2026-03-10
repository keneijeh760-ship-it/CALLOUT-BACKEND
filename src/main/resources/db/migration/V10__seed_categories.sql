INSERT INTO categories (org_id, name, slug, description)
SELECT
    o.id,
    c.name,
    c.slug,
    c.description
FROM organisations o
CROSS JOIN (VALUES
    ('Facilities',      'facilities',   'Broken equipment, maintenance issues, infrastructure problems'),
    ('IT',              'it',           'Network outages, hardware faults, software issues'),
    ('Disciplinary',    'disciplinary', 'Misconduct, policy violations, behavioural incidents'),
    ('Health & Safety', 'health-safety','Hygiene issues, safety hazards, medical concerns'),
    ('Other',           'other',        'Anything that does not fit the categories above')
) AS c(name, slug, description)
WHERE o.slug = 'pau';