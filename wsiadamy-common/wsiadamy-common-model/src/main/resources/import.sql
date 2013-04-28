-- user 1
INSERT INTO "public"."users" ("id", "password", "password_salt") VALUES ('1', null, null);
INSERT INTO "public"."users_logins" ("id", "accountscope", "password", "password_salt", "username", "userowner_id") VALUES ('1', '0', '260dcf28b0369dc344d4001b231024a2', 'ce588', 'athlan@vgroup.pl', '1');
INSERT INTO "public"."users_data" VALUES ('1', '1991-04-21', 'athlan@vgroup.pl', '660011034', null, 'Piotr', 'Pelczar', '1');
UPDATE "public"."users" SET userdata_id = '1' WHERE id = '1';
-- user 2
INSERT INTO "public"."users" ("id", "password", "password_salt") VALUES ('2', null, null);
INSERT INTO "public"."users_logins" ("id", "accountscope", "password", "password_salt", "username", "userowner_id") VALUES ('2', '1', null, null, '100000302544693', '2');
INSERT INTO "public"."users_data" VALUES ('2', '1991-04-21', 'me@athlan.pl', '660011034', '100000302544693', 'Piotr', 'Pelczar', '2');
UPDATE "public"."users" SET userdata_id = '2' WHERE id = '2';

-- ----------------------------
-- Records of route
-- ----------------------------
INSERT INTO "public"."route" VALUES ('1', NOW() + '+2 days'::interval, '2', '0', '1', '1', null, null, null, null);

-- ----------------------------
-- Records of route_details
-- ----------------------------
INSERT INTO "public"."route_details" VALUES ('1', '1', '1', '86.31', '1');

-- ----------------------------
-- Records of route_line
-- ----------------------------
INSERT INTO "public"."route_line" VALUES ('1', '010200000046000000D5B2B5BE483832408143A852B30B494083FA96395D3632403C53E8BCC60A494060CD018239323240528369183E0A4940B5E0455F413232404D158C4AEA0849406FD8B628B3313240E627D53E1D074940D2747632383232407E3F355EBA05494023C32ADEC8343240323D6189070449400490DAC4C9353240E65C8AABCA0249407F6FD39FFD3832406F9EEA909B014940EE258DD13A3A32402A1DACFF73004940C93CF207033F324056302AA91300494045A33B889D413240973E74417DFF484023BE13B35E4432402F56D4601AFE48400022FDF675483240959F54FB74FC4840D00F2384474B32406C60AB048BFB4840040473F4F84D32409817601F9DFA4840D6CA845FEA4F32406F9EEA909BF948405EA27A6B605332408CF84ECC7AF94840AAD4EC8156583240D97C5C1B2AFA484017BCE82B485B32408B3C49BA66FA4840E86F4221025E3240591CCEFC6AFA4840A7052FFA0A62324018EC866D8BFA48404D327216F6643240344B02D4D4FA4840467C2766BD683240252367614FFB484034333333336B324088BF266BD4FB48404B07EBFF1C6E32405D1B2AC6F9FB4840558CF337A1703240F9FC304278FC48400A5053CBD6723240D4D9C9E028FD4840070DFD135C7432402788BA0F40FE4840E982FA9639753240973E74417DFF4840C72E51BD357832405687DC0C370049407BDAE1AFC97A3240F2D7648D7A0049406A3524EEB17C32406F4C4F58E201494090A50F5D507F32400B80F10C1A024940D21DC4CE148232406E6E4C4F58024940A3B437F8C284324044E21E4B1F0249408126C286A7873240121956F1460249402692E865148B32403641D47D000249404AB54FC7638E3240A189B0E1E9014940193E22A644923240E71DA7E84802494005E275FD82953240E6B33C0FEE0249409C38B9DFA1983240CF6BEC12D5034940D10A0C59DD9A3240E38DCC237F0449404CAB21718F9D3240E9F692C66805494098FF907EFBA23240D23AAA9A20064940308672A25DA532409FCDAACFD50649407F4DD6A887A83240640B410E4A084940E1F3C308E1A93240DAC9E028790949406F1283C0CAA932402C6A300DC30B4940A60F5D50DFAA3240CD7A3194130D4940F4C308E1D1AE3240029F1F46080F4940AAD903ADC0B0324080130A1170104940D2E3F736FDB13240125322895E124940ECA86A82A8B332400135B56CAD1349402063EE5A42B632407F18213CDA14494082CAF8F719B7324035D252793B164940CC28965B5AB5324073A25D859417494097218E7571B33240B008FEB792194940E162450DA6B132402D6002B7EE1A49408D2D043928B13240D595CFF23C1C4940B3BAD573D2B3324084BBB376DB1D4940DA942BBCCBB532409FCDAACFD51E49409E29745E63B73240EB5BE67459204940C59448A297B93240F7CC92003521494079060DFD13BC32405A8638D6C5214940D5601A868FB832402E04392861224940A11518B2BAB53240A70A46257522494009C4EBFA05B33240977329AE2A2349404B9352D0EDAD3240394A5E9D632449408811C2A38DAB32406AC6A2E9EC244940', '1');

-- ----------------------------
-- Records of route_waypoint
-- ----------------------------
INSERT INTO "public"."route_waypoint" VALUES ('1', 'Gliwice, Polska', '0101000000802EA292DFAB32403EB676ECB1254940', '1');
INSERT INTO "public"."route_waypoint" VALUES ('2', 'Racibórz, Polska', '0101000000C0599F724C383240CCE8EC09B70B4940', '1');
INSERT INTO "public"."route_waypoint" VALUES ('3', 'Krzyżanowice, Polska', '0101000000C03E8ADFB9443240068B1E53D2FD4840', '1');

UPDATE "public"."route" SET waypointsource_id = '1', waypointdestination_id = '1', routeline_id = '1', routedetails_id = '1' WHERE id = '1';

INSERT INTO "public"."participanse" VALUES ('1', NOW(), NOW() - '1 minute'::interval, '1', '1', '1');

INSERT INTO "public"."participanse" VALUES ('2', NOW(), NOW() - '1 minute'::interval, '0', '1', '2');

